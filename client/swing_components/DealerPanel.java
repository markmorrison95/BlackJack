package client.swing_components;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

/**
 * DealerPanel
 * 
 * Creates and formats the panels for displaying the dealers cards. Also displays the current score for the dealer
 * and the no of players in the game.
 * 
 * Has getters for accessing the card panels and the labels. returns the card panels as an Array of JPanels
 */
public class DealerPanel extends JPanel {
    private JPanel cardOne, cardTwo, cardThree, cardFour, cardFive, cardSix, cardSeven, dealerInfo, noPlayersPanel;
    private JLabel dealerScore, noPlayers;
    private Color darkGreen = new Color(0, 102, 0);

    public DealerPanel(){
        this.setBackground(darkGreen);
        this.setBorder(new EmptyBorder(10, 30, 0, 30));
        GridLayout layout = new GridLayout(1,9);
        this.setBackground(darkGreen);
        layout.setHgap(10);
        this.setLayout(layout);

        noPlayersPanel = new JPanel();
        noPlayersPanel.setBackground(darkGreen);
        noPlayersPanel.setLayout(new GridLayout(2,1));
        JLabel noPlayersConstant = new JLabel("No. of Players:", SwingConstants.CENTER);
        formatComponent(noPlayersConstant, 12);
        noPlayers = new JLabel("", SwingConstants.CENTER);
        formatComponent(noPlayers, 20);
        noPlayersPanel.add(noPlayersConstant);
        noPlayersPanel.add(noPlayers);


        cardOne = new JPanel();
        cardOne.setLayout(new GridLayout());
        cardOne.setVisible(false);
        cardTwo = new JPanel();
        cardTwo.setLayout(new GridLayout());
        cardTwo.setVisible(false);
        cardThree = new JPanel();
        cardThree.setLayout(new GridLayout());
        cardThree.setVisible(false);
        cardFour = new JPanel();
        cardFour.setLayout(new GridLayout());
        cardFour.setVisible(false);
        cardFive = new JPanel();
        cardFive.setLayout(new GridLayout());
        cardFive.setVisible(false);
        cardSix = new JPanel();
        cardSix.setLayout(new GridLayout());
        cardSix.setVisible(false);
        cardSeven = new JPanel();
        cardSeven.setLayout(new GridLayout());
        cardSeven.setVisible(false);
        dealerInfo = new JPanel();
        dealerInfo.setBackground(darkGreen);
        dealerInfo.setLayout(new GridLayout(2,1));
        JLabel dealerConstant = new JLabel("Dealer\nScore:", SwingConstants.CENTER);
        formatComponent(dealerConstant, 12); 
        dealerScore = new JLabel("", SwingConstants.CENTER);
        formatComponent(dealerScore, 20);
        dealerInfo.add(dealerConstant);
        dealerInfo.add(dealerScore);
        this.add(noPlayersPanel);
        this.add(cardOne);
        this.add(cardTwo);
        this.add(cardThree);
        this.add(cardFour); 
        this.add(cardFive);
        this.add(cardSix);
        this.add(cardSeven);
        this.add(dealerInfo);
    }

    private void formatComponent(JComponent c, int fontSize){
        c.setBackground(darkGreen);
        c.setFont(new Font("Serif", Font.BOLD, fontSize));
        c.setForeground(Color.yellow);
    }

    public JPanel[] getDealerCardPanels(){
        JPanel[] dCards = new JPanel[7];
        dCards[0] = this.cardOne;
        dCards[1] = this.cardTwo;
        dCards[2] = this.cardThree;
        dCards[3] = this.cardFour;
        dCards[4] = this.cardFive;
        dCards[5] = this.cardSix;
        dCards[6] = this.cardSeven;
        return dCards;
    }

    public JLabel getDealerScoreLabel(){
        return this.dealerScore;
    }
    public JLabel getNoPlayersLabel(){
        return this.noPlayers;
    }
}