package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Bet;
import model.GameStats;
import model.StickOrHit;

/**
 * ClientRunner
 */
public class ClientRunner implements Runnable {
    private Socket socket = null;
    private GameServer parent = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private boolean waiting, firstRoundWinner;
    private int ID;

    public ClientRunner(Socket socket, GameServer parent, int ID) {
        this.socket = socket;
        this.parent = parent;
        this.ID = ID;
        try {
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            /** 
             * the main loop for receiving info from the client
             * 
             * initially waits for a bet object to be received as this signifies the start of a round
             * then gets caught by a loop which waits until the check for a 21 from the deal has happened
             * if there is a 21 from the deal then it needs to go back to waiting for a bet because it 
             * moves to the next round, otherwise it will go to waiting for a stickOrHit object
             * 
             * Im sure there is a better way of making it wait until it knows if there is a 
             * first round winner or not. I tried to use wait and notify but i couldn't get it to work 
             * 
             * keeps looping while the player hits and if they stick (end of clients round) will break 
             * and go back to start
             */
            while (true) {
                waiting = true;
                Bet bet = (Bet) inputStream.readObject();
                parent.makeBet(bet);
                StickOrHit stickOrHit = null;
                boolean stick = false;
                while (waiting) {
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!firstRoundWinner){
                    while (!stick) {
                        stickOrHit = (StickOrHit) inputStream.readObject();
                        if (stickOrHit.getOperation() == 1) {
                            parent.hitCards(stickOrHit);
                            continue;
                        }
                        if (stickOrHit.getOperation() == -1) {
                            parent.stickCards();
                            stick = true;

                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            parent.clientHasLeft(ID);
        }
    }
    public void setFirstRoundWinner(boolean b){
        /**
         * called to say whether there was a winner in the first round or now
         * changes the waiting variable to false so the run method can continue
         */
        firstRoundWinner = b;
        waiting = false;
    }
    public void closeClient(){
        // can close the socket and remove the client from outside the class
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transmitGameStats(GameStats gs) {
        // transmit game stats to client connected to this thread
        try {
            outputStream.writeUnshared(gs);
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getID() {
        return this.ID;
    }

}