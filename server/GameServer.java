package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import controller.Controller;
import model.UserOperation;
import model.GameStats;
/**
 * GameServer
 */
public class GameServer implements Runnable {
    private static int clientID = 1;
    private Controller controller;
    private GameStats gameStats;
    private ServerSocket serverSocket = null;
    private ArrayList<ClientRunner> clients = new ArrayList<ClientRunner>();

    public GameServer(Controller controller, GameStats gameStats) {
        this.controller = controller;
        this.gameStats = gameStats;
        try {
            serverSocket = new ServerSocket(8765);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            Socket clientSocket = null;
            try {
                while(true){
                    /**
                     * continuously looks for a client to connect, when one is accepted then sends them 
                     * their clientID, adds a new user to the game, creates a new ClientRunner for them,
                     * adds them to the clients list and starts a thread for them
                     * 
                     * increases clientID each round so this a unique variable
                     */
                clientSocket = serverSocket.accept();
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeInt(clientID);
                outputStream.flush();
                controller.addUser(clientID);
                ClientRunner client = new ClientRunner(clientSocket, this, clientID);
                clientID++;
                clients.add(client);
                new Thread(client).start();
                transmitStatsToAll();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void hitCards(UserOperation userOp){
        /**
         * passes a stickOrHitObject to the controller method
         */
        controller.hitCards(userOp);
    }
    public void stickCards(){
        //tells the controller that the active player is sticking
        controller.stickCards(); 
    }

    public void makeBet(UserOperation bet){
        // passes bet object to controller
        controller.placeBet(bet);
    }
    public synchronized void clientHasLeft(int ID){
        /**
         * called when a client closes their window and leaves the game
         * this removes them from the clients list and removes them
         * from the gameStats hashMap
         */
        for(ClientRunner cr : clients){
            if(cr.getID() == ID){
                clients.remove(cr);
                break;
            }
        }
        gameStats.remove(ID);
        transmitStatsToAll();
    }

    public synchronized void removeClient(int ID){
        /**
         * removes a client manually by closing their connection
         * and removing them from the clients list
         */
        for(ClientRunner cr : clients){
            if(cr.getID() == ID){
                cr.closeClient();
                clients.remove(cr);
                break;
            }
        }
    }


    public void transmitStatsToAll() {
        // send game stats to all clients connected
        for (ClientRunner cr : clients) {
                cr.transmitGameStats(gameStats);
        }
    }
}
