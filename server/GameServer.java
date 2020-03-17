package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import controller.Controller;
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
                clientSocket = serverSocket.accept();
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeInt(clientID);
                outputStream.flush();
                controller.addUser(clientID);
                clientID++;
                ClientRunner client = new ClientRunner(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void newGame(){
        controller.dealCards();
    }

    public void transmitStatsToAll() {
        // send game stats to all clients connected
        for (ClientRunner cr : clients) {
            if (cr != null) {
                cr.transmitGameStats(gameStats);
            }
        }
    }

}
