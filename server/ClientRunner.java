package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.UserOperation;
import model.GameStats;

/**
 * ClientRunner
 */
public class ClientRunner implements Runnable {
    private Socket socket = null;
    private GameServer parent = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
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
             * constantly looks for a UserOperation object to be passed. If it contains a 1 as the operation
             * means the user has hit cards button, if -1 has use the stick button. Otherwise it 
             * is a bet being passed
             */
            while (true) {
                UserOperation userOp = (UserOperation) inputStream.readObject();
                if (userOp.getUserOperation() == 1) {
                    parent.hitCards(userOp);
                    continue;
                }
                if (userOp.getUserOperation() == -1) {
                    parent.stickCards();
                }else{
                parent.makeBet(userOp);
                }
            }
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            parent.clientHasLeft(ID);
        }
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