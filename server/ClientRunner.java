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
            parent.clientLeft(ID);
        }
    }
    public void setFirstRoundWinner(boolean b){
        firstRoundWinner = b;
        waiting = false;
    }
    public void closeClient(){
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