package client.swing_components;

import javax.swing.*;
import java.awt.*;

/**
 * Swing
 */
public class MainPanel extends JPanel {
    DealerPanel dealer;
    JPanel gameInfo;
    BettingPanel bettingPanel;
    ClientCardPanel playerCards;
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


        playerCards = new ClientCardPanel();
        bettingPanel = new BettingPanel();


        this.add(dealer);
        this.add(gameInfo);
        this.add(playerCards);
        this.add(bettingPanel);
    }
    public JButton getUserHitButton(){
        return playerCards.getHitButton();
    }
    public JButton getUserStickButton(){
        return playerCards.getStickButton();   
    }
    public JLabel getDealerScoreLabel(){
        return dealer.getDealerScoreLabel();
    }
    public JButton getFiftyButton(){
        return bettingPanel.getFiftyButton();
    }
    public JButton getTwentyButton(){
        return bettingPanel.getTwentyButton();
    }
    public JButton getTenButton(){
        return bettingPanel.getTenButton();
    }
    public JLabel getCurrentBalanceLabel(){
        return bettingPanel.getCurrentBalanceLabel();
    }
    public JLabel getCurrentBetLabel(){
        return bettingPanel.getCurrentBetLabel();
    }
    public JLabel getGameInfoLabel(){
        return this.gameInfoLabel;
    }
    public JLabel getUserCurrentScoreLabel(){
        return bettingPanel.getCurrentScoreLabel();
    }

    public JPanel[] getUserCardPanels(){
        return playerCards.getUserCardPanels();
    }

    public JPanel[] getDealerCardPanels(){
        return dealer.getDealerCardPanels();
    }


}