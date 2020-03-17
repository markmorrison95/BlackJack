package swing_components;

import javax.swing.*;
import java.awt.*;

/**
 * Swing
 */
public class MainPanel extends JPanel {
    DealerPanel dealer;
    JPanel gameInfo;
    BettingPanel bettingPanel;
    CardPanel playerCards;
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
        bettingPanel = new BettingPanel();


        this.add(dealer);
        this.add(gameInfo);
        this.add(playerCards);
        this.add(bettingPanel);
    }

    public JPanel getUserCardOne(){
        return playerCards.getCardOne();
    }
    public JPanel getUserCardTwo(){
        return playerCards.getCardTwo();
    }
    public JPanel getUserCardThree(){
        return playerCards.getCardThree();
    }
    public JPanel getUserCardFour(){
        return playerCards.getCardFour();
    }
    public JPanel getUserCardFive(){
        return playerCards.getCardFive();
    }
    public JButton getUserHitButton(){
        return playerCards.getHitButton();
    }
    public JButton getUserStickButton(){
        return playerCards.getStickButton();   
    }
    public JPanel getDealerCardOne(){
        return dealer.getCardOne();
    }
    public JPanel getDealerCardTwo(){
        return dealer.getCardTwo();
    }
    public JPanel getDealerCardThree(){
        return dealer.getCardThree();
    }
    public JPanel getDealerCardFour(){
        return dealer.getCardFour();
    }
    public JPanel getDealerCardFive(){
        return dealer.getCardFive();
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
}