import javax.swing.*;
import java.awt.*;

/**
 * Swing
 */
public class MainPanel extends JPanel {
    JPanel main, dealer, gameInfo, playerCards, moneyPot;
    JLabel gameInfoLabel;

    public MainPanel() {
        Color darkGreen = new Color(0, 102, 0);
        this.setBackground(darkGreen);
        this.setLayout(new GridLayout(4, 1));
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


        this.add(dealer);
        this.add(gameInfo);
        this.add(playerCards);
        this.add(moneyPot);
    }
}