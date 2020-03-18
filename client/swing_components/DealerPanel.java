package client.swing_components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

/**
 * cardPanel
 */
public class DealerPanel extends JPanel{
    private JPanel cardOne, cardTwo, cardThree, cardFour, cardFive, dealerInfo, noPlayersPanel;
    private JLabel dealerScore, noPlayers;

    public DealerPanel(){
        Color darkGreen = new Color(0, 102, 0);
        this.setBackground(darkGreen);
        this.setBorder(new EmptyBorder(10, 30, 0, 30));
        GridLayout layout = new GridLayout(1,7);
        layout.setHgap(25);
        this.setLayout(layout);

        noPlayersPanel = new JPanel();
        noPlayersPanel.setBackground(darkGreen);
        noPlayersPanel.setLayout(new GridLayout(2,1));
        JLabel noPlayersConstant = new JLabel("No. of Players:", SwingConstants.CENTER);
        noPlayersConstant.setFont(new Font("Serif", Font.BOLD, 12));
        noPlayersConstant.setForeground(Color.yellow);
        noPlayers = new JLabel("", SwingConstants.CENTER);
        noPlayers.setFont(new Font("Serif", Font.BOLD, 20));
        noPlayers.setForeground(Color.yellow);
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
        dealerInfo = new JPanel();
        dealerInfo.setBackground(darkGreen);
        dealerInfo.setLayout(new GridLayout(2,1));
        JLabel dealerConstant = new JLabel("Dealer\nScore:", SwingConstants.CENTER);
        dealerConstant.setFont(new Font("Serif", Font.BOLD, 12));
        dealerConstant.setForeground(Color.yellow);
        dealerScore = new JLabel("11", SwingConstants.CENTER);
        dealerScore.setFont(new Font("Serif", Font.BOLD, 20));
        dealerScore.setForeground(Color.yellow);
        dealerInfo.add(dealerConstant);
        dealerInfo.add(dealerScore);
        this.add(noPlayersPanel);
        this.add(cardOne);
        this.add(cardTwo);
        this.add(cardThree);
        this.add(cardFour); 
        this.add(cardFive);
        this.add(dealerInfo);
    }

    public JPanel[] getDealerCardPanels(){
        JPanel[] dCards = new JPanel[5];
        dCards[0] = this.cardOne;
        dCards[1] = this.cardTwo;
        dCards[2] = this.cardThree;
        dCards[3] = this.cardFour;
        dCards[4] = this.cardFive;
        return dCards;
    }

    public JLabel getDealerScoreLabel(){
        return this.dealerScore;
    }
    public JLabel getNoPlayersLabel(){
        return this.noPlayers;
    }
}