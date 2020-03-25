package client.swing_components;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

/**
 * BettingPanel is the bottom panel on the window which displays the current balance, current bet and the current score.
 * It also has the betting buttons (50, 20, 10) and the deal button
 * 
 * this class creates the components and handles formatting and layout. It also provides getters and setters for the
 * buttons and labels which need to be accessed from outside this class.
 */
public class BettingPanel extends JPanel {
    private JButton fifty, twenty, ten, deal;
    private JLabel currentBalance, currentBet, currentScore;
    private Color darkGreen = new Color(0, 102, 0);
    public BettingPanel() {
        this.setBackground(darkGreen);
        this.setLayout(new GridLayout(2,1));

        JPanel top = new JPanel();
        top.setBackground(darkGreen);
        top.setLayout(new GridLayout(1, 2));
        JPanel topLeft = new JPanel();
        topLeft.setLayout(new GridLayout(1, 2));
        topLeft.setBackground(darkGreen);
        topLeft.setBorder(new EmptyBorder(10, 30, 10, 10));
        JPanel topRight = new JPanel();
        topRight.setBackground(darkGreen);
        topRight.setLayout(new GridLayout(1, 2));

        JLabel currentScoreConstant = new JLabel("Your Current Score:");
        labelFormatting(currentScoreConstant);
        
        currentScore = new JLabel("");
        labelFormatting(currentScore);

        topRight.add(currentScoreConstant);
        topRight.add(currentScore);

        JLabel currentBalanceConstant = new JLabel("Your Balance:", SwingConstants.RIGHT);
        labelFormatting(currentBalanceConstant);

        currentBalance = new JLabel("");
        labelFormatting(currentBalance);


        topLeft.add(currentBalanceConstant);
        topLeft.add(currentBalance);


        top.add(topLeft);
        top.add(topRight);

        JLabel currentBetConstant = new JLabel("CurrentBet:", SwingConstants.LEFT);
        labelFormatting(currentBetConstant);

        currentBet = new JLabel();
        labelFormatting(currentBet);

        JPanel bottom = new JPanel();
        bottom.setBackground(darkGreen);
        bottom.setLayout(new GridLayout(1, 3));

        JPanel bottomButtons = new JPanel();
        bottomButtons.setBackground(darkGreen);
        bottomButtons.setLayout(new GridLayout(1, 2));
        bottomButtons.setBorder(new EmptyBorder(10, 10, 10, 10));

        fifty = new JButton("Bet 50");
        twenty = new JButton("Bet 20");
        ten = new JButton("Bet 10");


        JPanel bottomRight = new JPanel();
        bottomRight.setBackground(darkGreen);
        bottomRight.setLayout(new GridLayout(1,3));
        bottomRight.setBorder(new EmptyBorder(10, 10, 10, 10));


        bottomButtons.add(fifty);
        bottomButtons.add(twenty);
        bottomButtons.add(ten);

        deal = new JButton("Deal");
        deal.setEnabled(false);

        bottomRight.add(currentBetConstant);
        bottomRight.add(currentBet);
        bottomRight.add(deal);
        
        bottom.add(bottomButtons);
        bottom.add(bottomRight);
        this.add(top);
        this.add(bottom);
    }

    private void labelFormatting(JLabel l){
        l.setFont(new Font("Serif", Font.BOLD, 18));
        l.setForeground(Color.yellow);
        l.setBackground(darkGreen);
    }

    public JButton getFiftyButton(){
        return this.fifty;
    }

    public JButton getTwentyButton(){
        return this.twenty;
    }

    public JButton getTenButton(){
        return this.ten;
    }
    public JButton getDealButton(){
        return this.deal;
    }

    public JLabel getCurrentBalanceLabel(){
        return this.currentBalance;
    }
    public JLabel getCurrentBetLabel(){
        return this.currentBet;
    }
    public JLabel getCurrentScoreLabel(){
        return this.currentScore;
    }
}