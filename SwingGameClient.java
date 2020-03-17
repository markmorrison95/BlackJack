import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

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
            ReadWorker readWorker = new ReadWorker(socket, parent);
            readWorker.execute();

        }

        public Void doInBackground(){


            return null;
        }
    }

    private Socket server = null;
    private ObjectOutputStream outputStream;

    public SwingGameClient() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel main = new MainPanel();
        this.add(main);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        connect();
    }

    public void connect() {
        try {
            server = new Socket("127.0.0.1", 8765);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    
}