package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import model.Card;
import model.GameStats;
import swing_components.*;

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
            int i = -1;
            try {
                gs = (GameStats)inputStream.readObject();
                // what to do with input ie. game stats
                System.out.println(gs.getPlayerMap().get(ID).get(0));
                parent.updateUserCards(gs);

            // } catch (ClassNotFoundException e) {
            //     e.printStackTrace();
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
    // private JPanel userCardOne, userCardTwo, userCardThree, userCardFour, userCardFive;
    private JPanel[] userCards;
    private JPanel dealerCardOne, dealerCardTwo, dealerCardThree, dealerCardFour, dealerCardFive;
    private JButton hitButton, stickButton, bet50Button, bet20Button, bet10Button;
    private JLabel dealerScoreLabel, currentBalanceLabel, currentBetLabel, gameInfoLabel, userScoreLabel;

    public SwingGameClient() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main = new MainPanel();
        this.add(main);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initializeComponents();
        connect();
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
            this.ID = (int)input.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateGameInfo(GameStats gs) {
        if (gs.getActivePlayer() == this.ID) {

        }
    }

    public void updateUserCards(GameStats gs){
        ArrayList<Card> uCards = gs.getPlayerMap().get(ID);
        for(int i = 0; i < uCards.size(); i++){
            userCards[i].add(new JLabel(uCards.get(i).toString()));
        }
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }






    public void initializeComponents() {
        // userCardOne = main.getUserCardOne();
        // userCardTwo = main.getUserCardTwo();
        // userCardThree = main.getUserCardThree();
        // userCardFour = main.getUserCardFour();
        // userCardFive = main.getUserCardFive();
        userCards = main.getUserCardPanels();

        dealerCardOne = main.getDealerCardOne();
        dealerCardTwo = main.getDealerCardTwo();
        dealerCardThree = main.getDealerCardThree();
        dealerCardFour = main.getDealerCardFour();
        dealerCardFive = main.getDealerCardFive();

        dealerScoreLabel = main.getDealerScoreLabel();
        userScoreLabel = main.getUserCurrentScoreLabel();

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