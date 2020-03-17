package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.GameStats;

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
        try{
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        //what happens while client is connected

        //********************************* */
        parent.newGame();
        parent.transmitStatsToAll();
    }

    public void transmitGameStats(GameStats gs){
        // transmit game stats to client connected to this thread
        try{
            outputStream.writeObject(gs);
            outputStream.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}