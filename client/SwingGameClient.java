package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;
import model.GameStats;
import swing_components.*;

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
            try {
                while ((gs = (GameStats) inputStream.readObject()) != null);
                    // what to do with input ie. game stats

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                return null;
            }
        }
    }

    private Socket server = null;
    private ObjectOutputStream outputStream;
    private int ID;

    public SwingGameClient(int ID) {
        this.ID = ID;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel main = new MainPanel();
        this.add(main);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateGameInfo(GameStats gs){
        if(gs.getActivePlayer() == this.ID){

        }
    }
    

    
}