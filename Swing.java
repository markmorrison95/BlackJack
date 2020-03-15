import javax.swing.*;

import org.w3c.dom.Text;

import java.awt.*;
import java.awt.GridLayout;

/**
 * Swing
 */
public class Swing extends JFrame {
    JPanel main, dealer, gameInfo, playerCards, moneyPot;
    JLabel gameInfoLabel;

    public Swing() {
        this.setTitle("BlackJack");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Color darkGreen = new Color(0, 102, 0);
        main = new JPanel();
        main.setBackground(darkGreen);
        main.setLayout(new GridLayout(4, 1));
        dealer = new DealerPanel();

        gameInfo = new JPanel();
        gameInfo.setBackground(darkGreen);
        gameInfo.setLayout(new GridLayout());
        gameInfoLabel = new JLabel("Game Info", SwingConstants.CENTER);
        gameInfoLabel.setFont(new Font("Serif", Font.BOLD, 35));
        gameInfoLabel.setForeground(Color.yellow);
        gameInfo.add(gameInfoLabel);


        playerCards = new CardPanel();
        moneyPot = new BettingPanel();


        main.add(dealer);
        main.add(gameInfo);
        main.add(playerCards);
        main.add(moneyPot);
        this.add(main);
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}