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
public class Client extends JFrame implements ActionListener {

   /****** Swing Worker Class *********************************************************** */ 
    private class ReadWorker extends SwingWorker<Void, Void> {
        private Socket socket = null;
        private ObjectInputStream inputStream = null;
        private Client parent = null;

        public ReadWorker(Socket socket, Client parent) {
            this.socket = socket;
            this.parent = parent;
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public Void doInBackground() {
            /**
             * handles the input of the game stats object and passes to the updateGameInfo method in 
             * the Client class. This always running in the background because it doesn't know when the 
             * server is going to send something
             */
            GameStats gs = null;
            try {
                while ((gs = (GameStats) inputStream.readUnshared()) != null) {
                    parent.updateGameInfo(gs);
                }
            } catch (IOException e) {
                gameInfoLabel.setText("You exited the Game");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    /**************** End of Swing Worker Class **************************************************/




    /******************** Variables required for the swing components **********************************/
    private int ID;
    private MainPanel main;
    private JPanel[] userCards, dealerCards;
    private JButton hitButton, stickButton, bet50Button, bet20Button, bet10Button, dealButton;
    private JLabel dealerScoreLabel, currentBalanceLabel, currentBetLabel, gameInfoLabel, activePlayerLabel,
            userScoreLabel, noPlayers;
    private int currentBet;
    /*****************************************************************************************************/

    private Socket server = null;
    private ObjectOutputStream outputStream;

    public Client() {
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


    /************************* controls the swing components for game play below **********************************/

    public void updateGameInfo(GameStats gs) {
        /**
         * the main method that deals with the running input of the game stats object
         */
        if (gs.hasGameBeenReset()) {
            /**
             * if the game is has ended and reset then this label is displayed otherwise the
             * rest of the method is worked through
             */
            gameInfoLabel.setText("You're Broke, Game Reset!");
        } else {
            /**
             * sets the label with the number of players. Each time a new player joins a
             * game stats object is sent so this label will update as player join
             */
            noPlayers.setText("" + (gs.size() - 1));
            currentBalanceLabel.setText("" + gs.get(ID).getBalance());
            /**
             * if the game is not waiting for bets then it must be game play
             */
            if (!gs.isWaitingForBets()) {
                /**
                 * sets the active player so the user knows whose turn it is ensures the bet
                 * buttons are disabled and updates the user cards and dealer cards
                 */
                int activePlayer = gs.getActivePlayer();
                setActivePlayerLabel(activePlayer);
                betButtonsEnabled(false, gs.get(ID).getBalance());
                updateUserCards(gs);
                updateDealerCards(gs);

                /**
                 * checks for a winner. Does this by checking the size of the winners array in
                 * the game stats object (if > 0 must be winner). does this first incase there
                 * is a straight 21 from the deal. If there is a winner sets the label and
                 * sleeps for 2 seconds so the user can read it
                 */
                if (gs.getWinners().size() > 0) {
                    setWinnersLabel(gs.getWinners());
                } else {
                    /**
                     * if no winner then checks for the active player. If it is the current user
                     * then moves to asking for them to hit or stick and enables these buttons.
                     * 
                     * If the user is bust (over 21) then disabled buttons and sets the label to
                     * display this again sleeping the thread to allow time to read. Sends a
                     * HitOrStick object with a stick operation to the server so it knows they cant
                     * get anymore cards
                     */
                    if ((activePlayer == ID) && !(gs.get(ID).isBust())) {
                        gameInfoLabel.setText("Choose Hit or Stick");
                        hitAndStickEnabled(true);
                    } else if ((activePlayer == ID) && gs.get(ID).isBust()) {
                        gameInfoLabel.setText("BUST!");
                        hitAndStickEnabled(false);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sendStickOrHit(-1);
                    }
                    /**
                     * if it is the dealer to go, sets the label to represent this and there cards
                     * will be updated according to play because they are sent to the
                     * updateDealerCard method at the top
                     */
                    else if (activePlayer == 0 && gs.get(ID).isBust()) {
                        gameInfoLabel.setText("You're Bust! Dealer to Play!");
                    } else if (activePlayer == 0 && !(gs.get(ID).isBust())) {
                        gameInfoLabel.setText("Dealer to Go!");
                    } else {
                        gameInfoLabel.setText("Waiting for Player " + activePlayer + " to Play Round");
                    }
                }
            } else {
                /**
                 * if here then start of round and waiting for bets. Sets active player and
                 * current score to nothing. Resets the current bet to 0 and enables the betting
                 * buttons
                 */
                setActivePlayerLabel(-1);
                userScoreLabel.setText("");
                removeCards();
                currentBet = 0;
                currentBetLabel.setText("" + currentBet);
                betButtonsEnabled(true, gs.get(ID).getBalance());
                hitAndStickEnabled(false);
                gameInfoLabel.setText("Place Bet and Click Deal to Start");
            }
        }
    }

    public void setWinnersLabel(ArrayList<Player> winners) {
        /**
         * takes in the array list of winners from the game stats object and works out
         * if the current client is a winner, loser or a draw. This is done depending on
         * the size of the array (1 = win, > 1 = draw etc) and the Player ID in the
         * object
         */
        boolean b = false;
        if (winners.size() == 1) {
            if (winners.get(0).getID() == this.ID) {
                gameInfoLabel.setText("Winner Winner Chicken Dinner!");
            } else {
                gameInfoLabel.setText("You Lost");
            }
        } else if (winners.size() > 1) {
            for (Player p : winners) {
                if (p.getID() == this.ID) {
                    gameInfoLabel.setText("You drew this round!");
                    b = true;
                }
            }
            if (!b) {
                gameInfoLabel.setText("You Lost!");
            }
        } else {
            gameInfoLabel.setText("You Lost!");
        }
    }

    public void setActivePlayerLabel(int id) {
        /**
         * lets the user know who the active player is depending on whether the active
         * player ID matches up with the ID for this client
         */
        String player = "" + id;
        if (id == this.ID) {
            player = "You";
        }
        if (id == 0) {
            player = "Dealer";
        }
        if (id == -1) {
            player = "";
        }
        activePlayerLabel.setText(player);
    }

    public void removeCards() {
        /**
         * clears all the CardPanel objects from the window and sets the panel to not
         * visible this is used at the end of the round when cards are collected back in
         */
        for (int i = 0; i < userCards.length; i++) {
            userCards[i].removeAll();
            userCards[i].setVisible(false);
            dealerCards[i].removeAll();
            dealerCards[i].setVisible(false);
        }
    }

    public void updateUserCards(GameStats gs) {
        /**
         * creates a new CardPanel component for each card in the users deck and sets
         * the current score label
         */
        ArrayList<Card> uCards = gs.get(ID);
        for (int i = 0; i < uCards.size(); i++) {
            userCards[i].removeAll();
            userCards[i].add(new CardPanel(uCards.get(i).getCardRank(), uCards.get(i).getCardSuit()));
            userCards[i].setVisible(true);
        }
        userScoreLabel.setText("" + gs.get(ID).getCurrentScore());
    }

    public void updateDealerCards(GameStats gs) {
        /**
         * sets the card components for the dealer. Deals the first card face down and
         * the second face up and only displays the current score for the 2nd card. When
         * it is the dealers turn places all cards face up.
         * 
         * for each card creates a new CardPanel component using the variables from the
         * card
         * 
         * If the dealer get a natural (21 on deal) then his cards will be dealt face up
         * before moving to the next round.
         */
        ArrayList<Card> dCards = gs.get(0);
        if ((gs.getActivePlayer() == 0) || (gs.get(0).getCurrentScore() == 21)) {
            dealerScoreLabel.setText("" + gs.get(0).getCurrentScore());
            for (int i = 0; i < dCards.size(); i++) {
                dealerCards[i].removeAll();
                dealerCards[i].add(new CardPanel(dCards.get(i).getCardRank(), dCards.get(i).getCardSuit()));
                dealerCards[i].setVisible(true);
            }
        } else {
            dealerScoreLabel.setText("" + dCards.get(1).getCardRank().cardValue());
            dealerCards[0].removeAll();
            dealerCards[0].add(new CardPanel());
            dealerCards[0].setVisible(true);
            dealerCards[1].removeAll();
            dealerCards[1].add(new CardPanel(dCards.get(1).getCardRank(), dCards.get(1).getCardSuit()));
            dealerCards[1].setVisible(true);
        }
    }

    public void betButtonsEnabled(boolean b, int balance) {
        /**
         * takes in a boolean value to decide whether the bet buttons are enabled or
         * disabled. only allows certain buttons depending on the balance available to
         * the player
         */
        if (!b) {
            bet10Button.setEnabled(b);
            bet20Button.setEnabled(b);
            bet50Button.setEnabled(b);
        }
        if (b && balance >= 50) {
            bet10Button.setEnabled(b);
            bet20Button.setEnabled(b);
            bet50Button.setEnabled(b);
        }
        if (b && balance < 50) {
            bet10Button.setEnabled(b);
            bet20Button.setEnabled(b);
            bet50Button.setEnabled(false);
        }
        if (b && balance < 20) {
            bet10Button.setEnabled(b);
            bet20Button.setEnabled(false);
            bet50Button.setEnabled(false);
        }
    }

    public void hitAndStickEnabled(boolean b) {
        hitButton.setEnabled(b);
        stickButton.setEnabled(b);
    }

    public void sendStickOrHit(int operation) {
        /**
         * takes an int as the arguments specifying the operation. then creates a
         * StickOrHit object with the players ID and the operation Then sends this back
         * to the server
         */
        try {
            outputStream.writeObject(new StickOrHit(this.ID, operation));
            outputStream.reset();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * defines the required action for each button
         * 
         * when a bet button is pressed it adds that amount to the class variable bet
         * and updates the current bet label. When deal is then pressed it sends a bet
         * object which is created with the bet variable and the client ID. And disables
         * the bet and deal buttons
         * 
         * when the hit or stick is clicked it sends a hitOrStick object to the server
         * when stick us clicked disables the hit and stick buttons
         */
        if (e.getSource() == dealButton) {
            try {
                outputStream.writeObject(new Bet(ID, currentBet));
                outputStream.reset();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                betButtonsEnabled(false, 0);
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
        /**
         * initiates all the swing components needed to be editable for updating with
         * current game info also initiates the buttons and adds action listeners to
         * them
         */
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
        new Client();
    }
}