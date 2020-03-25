package client.swing_components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

/**
 * mainPanel
 * 
 * this adds al the components from the other swing classes together and formats them in one panel. 
 * it also adds a middle panel with the game info and active player labels
 * 
 * getters to access all the components from all the panels
 */
public class MainPanel extends JPanel {
    DealerPanel dealer;
    JPanel gameInfo;
    BettingPanel bettingPanel;
    ClientCardPanel playerCards;
    JLabel gameInfoLabel;
    JLabel activePlayer;

    public MainPanel() {
        Color darkGreen = new Color(0, 102, 0);
        this.setBackground(darkGreen);
        this.setLayout(new GridLayout(4, 1));
        dealer = new DealerPanel();

        gameInfo = new JPanel();
        gameInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        gameInfo.setBackground(darkGreen);
        gameInfoLabel = new JLabel("Place Bet and CLick Deal to Start Once Players have Joined");
        gameInfoLabel.setFont(new Font("Serif", Font.BOLD, 20));
        gameInfoLabel.setForeground(Color.yellow);
        JPanel activePlayerPanel = new JPanel();
        activePlayerPanel.setLayout(new GridLayout(2, 1));
        activePlayerPanel.setBackground(darkGreen);
        JLabel activePlayerConstant = new JLabel("Active Player:", SwingConstants.LEFT);        
        activePlayerConstant.setFont(new Font("Serif", Font.BOLD, 14));
        activePlayerConstant.setForeground(Color.yellow);

        activePlayer = new JLabel("", SwingConstants.LEFT);
        activePlayer.setFont(new Font("Serif", Font.BOLD, 14));
        activePlayer.setForeground(Color.yellow);
        activePlayerPanel.add(activePlayerConstant);
        activePlayerPanel.add(activePlayer);

        JPanel blank = new JPanel();
        blank.setBorder(new EmptyBorder(20, 20, 20, 20));
        blank.setBackground(darkGreen);
        
        gameInfo.add(activePlayerPanel);
        gameInfo.add(blank);
        gameInfo.add(gameInfoLabel);


        playerCards = new ClientCardPanel();
        bettingPanel = new BettingPanel();


        this.add(dealer);
        this.add(gameInfo);
        this.add(playerCards);
        this.add(bettingPanel);
    }
    public JLabel getActivePlayer(){
        return this.activePlayer;
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
    public JButton getDealButton(){
        return bettingPanel.getDealButton();
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
    public JLabel getNoPlayersLabel(){
        return dealer.getNoPlayersLabel();
    }

    public JPanel[] getUserCardPanels(){
        return playerCards.getUserCardPanels();
    }

    public JPanel[] getDealerCardPanels(){
        return dealer.getDealerCardPanels();
    }


}