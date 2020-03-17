package swing_components;

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
    private JPanel cardOne, cardTwo, cardThree, cardFour, cardFive, dealerInfo;
    private JLabel dealerScore;

    public DealerPanel(){
        Color darkGreen = new Color(0, 102, 0);
        this.setBackground(darkGreen);
        this.setBorder(new EmptyBorder(10, 130, 0, 30));
        GridLayout layout = new GridLayout(1,6);
        layout.setHgap(25);
        this.setLayout(layout);
        cardOne = new JPanel();
        cardTwo = new JPanel();
        cardThree = new JPanel();
        cardFour = new JPanel();
        cardFive = new JPanel();
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
        this.add(cardOne);
        this.add(cardTwo);
        this.add(cardThree);
        this.add(cardFour); 
        this.add(cardFive);
        this.add(dealerInfo);
    }

    public JPanel getCardOne(){
        return this.cardOne;
    }
    public JPanel getCardTwo(){
        return this.cardTwo;
    }
    public JPanel getCardThree(){
        return this.cardThree;
    }
    public JPanel getCardFour(){
        return this.cardFour;
    }
    public JPanel getCardFive(){
        return this.cardFive;
    }
    public JLabel getDealerScoreLabel(){
        return this.dealerScore;
    }
}