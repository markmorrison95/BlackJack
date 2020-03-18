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

    public ClientRunner(Socket socket, GameServer parent) {
        this.socket = socket;
        this.parent = parent;
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
                Bet bet = (Bet) inputStream.readObject();
                parent.makeBet(bet);
                StickOrHit stickOrHit = null;
                while ((stickOrHit = (StickOrHit) inputStream.readObject()) != null) {
                    if (stickOrHit.getOperation() == 1) {
                        parent.hitCards(stickOrHit);
                    } else {
                        parent.stickCards(stickOrHit);
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transmitGameStats(GameStats gs) {
        // transmit game stats to client connected to this thread
        try {
            System.out.println(gs.get(1).size());
            outputStream.writeUnshared(gs);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}