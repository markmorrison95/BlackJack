package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.GameStats;

/**
 * GameServer
 */
public class GameServer implements Runnable {

    private ServerSocket serverSocket = null;
    private ArrayList<ClientRunner> clients = new ArrayList<ClientRunner>();

    public GameServer() {
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
                ClientRunner client = new ClientRunner(clientSocket);
                clients.add(client);
                new Thread(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void transmitStatsToAll(GameStats gs) {
        // send game stats to all clients connected
        for (ClientRunner cr : clients) {
            if (cr != null) {
                cr.transmitGameStats(gs);
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(new GameServer());
        t.start();
        try {
            t.join();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}
