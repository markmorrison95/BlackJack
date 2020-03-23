package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import model.Bet;
import model.Card;
import model.GameStats;
import model.Player;
import model.StickOrHit;
import client.swing_components.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GameClient
 */
public class SwingGameClient extends JFrame implements ActionListener {
    private class ReadWorker extends SwingWorker<Void, Void> {
        private Socket socket = null;
        private ObjectInputStream inputStream = null;
        private SwingGameClient parent = null;

        public ReadWorker(Socket socket, SwingGameClient parent) {
            this.socket = socket;
            this.parent = parent;
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public Void doInBackground() {
            GameStats gs = null;
            try {
                while ((gs = (GameStats) inputStream.readUnshared()) != null) {
                    // what to do with input ie. game stats
                    /******************************************************** */
                    parent.updateGameInfo(gs);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return null;
            }
        }
    }

    private Socket server = null;
    private ObjectOutputStream outputStream;
    private int ID;
    private MainPanel main;
    private JPanel[] userCards, dealerCards;
    private JButton hitButton, stickButton, bet50Button, bet20Button, bet10Button, dealButton;
    private JLabel dealerScoreLabel, currentBalanceLabel, currentBetLabel, gameInfoLabel, activePlayerLabel,
            userScoreLabel, noPlayers;
    private int currentBet;

    public SwingGameClient() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main = new MainPanel();
        this.add(main);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initializeComponents();
        connect();
        this.setTitle("Blackjack - Player " + ID);
        try {
            outputStream = new ObjectOutputStream(server.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ReadWorker readWorker = new ReadWorker(server, this);
        readWorker.execute();
    }

    public void connect() {
        try {
            server = new Socket("127.0.0.1", 8765);
            ObjectInputStream input = new ObjectInputStream(server.getInputStream());
            this.ID = (int) input.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateGameInfo(GameStats gs) {
        noPlayers.setText("" + (gs.size() - 1));
        currentBalanceLabel.setText("" + gs.get(ID).getBalance());
        if (!gs.isWaitingForBets()) {
            int activePlayer = gs.getActivePlayer();
            setActivePlayerLabel(activePlayer);
            betButtonsEnabled(false);
            updateUserCards(gs);
            updateDealerCards(gs);
            if(gs.isEndOfRound()){
                setWinnersLabel(gs.getWinners());
                }else{
                if ((activePlayer == ID) && !(gs.get(ID).isBust())) {
                    gameInfoLabel.setText("Choose Hit or Stick");
                    hitAndStickEnabled(true);
                }else if ((activePlayer == ID) && gs.get(ID).isBust()) {
                    gameInfoLabel.setText("BUST!");
                    hitAndStickEnabled(false);
                    sendStickOrHit(-1);
                }
                else{
                    gameInfoLabel.setText("Waiting for Player " + activePlayer + " to Play Round");
                }
            }
        }else{
                removeCards();
                currentBet = 0;
                currentBetLabel.setText("" + currentBet);
                betButtonsEnabled(true);
                hitAndStickEnabled(false);
                gameInfoLabel.setText("Place Bet and Click Deal to Start");
            }
        }

    public void setWinnersLabel(ArrayList<Player> winners){
        for(Player p:winners){
            System.out.println(p.getID());
        }
        boolean b = false;
        if(winners.size() == 1){
            if(winners.get(0).getID() == this.ID){
                gameInfoLabel.setText("Winner Winner Chicken Dinner!");
            }else{
                gameInfoLabel.setText("You Lost");
            }
        }else if(winners.size() > 1){
            for(Player p: winners){
                if(p.getID() == this.ID){
                    gameInfoLabel.setText("You drew this round!");
                    b = true;
                }
            }if(!b){gameInfoLabel.setText("You Lost!");}
        }else{gameInfoLabel.setText("You Lost!");}
    }

    public void setActivePlayerLabel(int id) {
        String player = "" + id;
        if (id == this.ID) {
            player = "You";
        }
        if (id == 0) {
            player = "Dealer";
        }
        activePlayerLabel.setText(player);
    }

    public void removeCards(){
        for(int i = 0; i < userCards.length; i++){
            userCards[i].removeAll();
            userCards[i].setVisible(false);
            dealerCards[i].removeAll();
            dealerCards[i].setVisible(false);
        }
    }

    public void updateUserCards(GameStats gs) {
        ArrayList<Card> uCards = gs.get(ID);
        for (int i = 0; i < uCards.size(); i++) {
            userCards[i].removeAll();
            /**
             * removes all components currently addded to stop doubling up of cards
             */
            userCards[i].add(new CardPanel(uCards.get(i).getCardRank(), uCards.get(i).getCardSuit()));
            userCards[i].setVisible(true);
        }
        userScoreLabel.setText("" + gs.get(ID).getCurrentScore());
    }

    public void updateDealerCards(GameStats gs) {
        ArrayList<Card> dCards = gs.get(0);
        if (gs.getActivePlayer() == 0) {
            for (int i = 0; i < dCards.size(); i++) {
                dealerCards[i].removeAll();
                /**
                 * removes all components currently addded to stop doubling up of cards
                 */
                dealerCards[i].add(new CardPanel(dCards.get(i).getCardRank(), dCards.get(i).getCardSuit()));
                dealerCards[i].setVisible(true);
            }
            dealerScoreLabel.setText("" + gs.get(0).getCurrentScore());
        } else if(gs.get(0).getCurrentScore() == 21){
            dealerScoreLabel.setText("" + gs.get(0).getCurrentScore());
            for(int i =0; i<2; i++){
                dealerCards[i].removeAll();
                dealerCards[i].add(new CardPanel(dCards.get(i).getCardRank(), dCards.get(i).getCardSuit()));
                dealerCards[i].setVisible(true);
            }
        }else{
            dealerScoreLabel.setText("" + dCards.get(1).getCardRank().cardValue());
            dealerCards[0].removeAll();
            dealerCards[0].add(new CardPanel());
            dealerCards[0].setVisible(true);
            dealerCards[1].removeAll();
            dealerCards[1].add(new CardPanel(dCards.get(1).getCardRank(), dCards.get(1).getCardSuit()));
            dealerCards[1].setVisible(true);
        }
    }

    public void betButtonsEnabled(boolean b) {
        bet10Button.setEnabled(b);
        bet20Button.setEnabled(b);
        bet50Button.setEnabled(b);
    }
    public void hitAndStickEnabled(boolean b){
        hitButton.setEnabled(b);
        stickButton.setEnabled(b);
    }

    public void sendStickOrHit(int operation){
        try {
            outputStream.writeObject(new StickOrHit(this.ID, operation));
            outputStream.reset();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dealButton) {
            try {
                outputStream.writeObject(new Bet(ID, currentBet));
                outputStream.reset();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                betButtonsEnabled(false);
                dealButton.setEnabled(false);
                gameInfoLabel.setText("Waiting for other Players to Place Bets");
            }
        }
        if (e.getSource() == hitButton) {
            sendStickOrHit(1);
        }
        if (e.getSource() == stickButton) {
            sendStickOrHit(-1);
            hitAndStickEnabled(false);
        }
        if (e.getSource() == bet10Button) {
            currentBet += 10;
            currentBetLabel.setText("" + currentBet);
            dealButton.setEnabled(true);
        }
        if (e.getSource() == bet20Button) {
            currentBet += 20;
            currentBetLabel.setText("" + currentBet);
            dealButton.setEnabled(true);
        }
        if (e.getSource() == bet50Button) {
            currentBet += 50;
            currentBetLabel.setText("" + currentBet);
            dealButton.setEnabled(true);
        }

    }

    public void initializeComponents() {
        currentBet = 0;
        userCards = main.getUserCardPanels();
        dealerCards = main.getDealerCardPanels();
        activePlayerLabel = main.getActivePlayer();
        dealerScoreLabel = main.getDealerScoreLabel();
        userScoreLabel = main.getUserCurrentScoreLabel();
        currentBalanceLabel = main.getCurrentBalanceLabel();
        currentBetLabel = main.getCurrentBetLabel();
        gameInfoLabel = main.getGameInfoLabel();
        noPlayers = main.getNoPlayersLabel();
        dealButton = main.getDealButton();
        dealButton.addActionListener(this);
        hitButton = main.getUserHitButton();
        hitButton.addActionListener(this);
        stickButton = main.getUserStickButton();
        stickButton.addActionListener(this);
        bet10Button = main.getTenButton();
        bet10Button.addActionListener(this);
        bet20Button = main.getTwentyButton();
        bet20Button.addActionListener(this);
        bet50Button = main.getFiftyButton();
        bet50Button.addActionListener(this);
    }

    public static void main(String[] args) {
        new SwingGameClient();
    }
}