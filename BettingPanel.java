import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

/**
 * BettingPanel
 */
public class BettingPanel extends JPanel {
    private JButton fifty, twenty, ten;
    private JLabel currentBalance, currentBet;

    public BettingPanel() {
        Color darkGreen = new Color(0, 102, 0);
        this.setBackground(darkGreen);
        this.setLayout(new GridLayout(2,1));

        JPanel top = new JPanel();
        top.setBackground(darkGreen);
        top.setLayout(new GridLayout(1, 2));
        JPanel topLeft = new JPanel();
        topLeft.setLayout(new GridLayout(2, 1));
        topLeft.setBackground(darkGreen);
        topLeft.setBorder(new EmptyBorder(10, 30, 10, 10));
        JPanel blank = new JPanel();
        blank.setBackground(darkGreen);

        JLabel currentBalanceConstant = new JLabel("Your Balance:", SwingConstants.LEFT);
        currentBalanceConstant.setFont(new Font("Serif", Font.BOLD, 18));
        currentBalanceConstant.setForeground(Color.yellow);
        currentBalanceConstant.setBackground(darkGreen);

        currentBalance = new JLabel("10", SwingConstants.LEFT);
        currentBalance.setFont(new Font("Serif", Font.BOLD, 18));
        currentBalance.setBackground(darkGreen);
        currentBalance.setForeground(Color.yellow);


        topLeft.add(currentBalanceConstant);
        topLeft.add(currentBalance);


        top.add(topLeft);
        top.add(blank);

        JLabel currentBetConstant = new JLabel("CurrentBet:", SwingConstants.RIGHT);
        currentBetConstant.setFont(new Font("Serif", Font.BOLD, 18));
        currentBetConstant.setForeground(Color.yellow);
        currentBetConstant.setBackground(darkGreen);

        currentBet = new JLabel("10", SwingConstants.CENTER);
        currentBet.setFont(new Font("Serif", Font.BOLD, 18));
        currentBet.setForeground(Color.yellow);
        currentBet.setBackground(darkGreen);

        JPanel bottom = new JPanel();
        bottom.setBackground(darkGreen);
        bottom.setLayout(new GridLayout(1, 2));

        JPanel bottomButtons = new JPanel();
        bottomButtons.setBackground(darkGreen);
        bottomButtons.setLayout(new GridLayout(1, 3));
        bottomButtons.setBorder(new EmptyBorder(10, 10, 10, 10));

        fifty = new JButton("Bet 50");
        twenty = new JButton("Bet 20");
        ten = new JButton("Bet 10");


        JPanel bottomRight = new JPanel();
        bottomRight.setBackground(darkGreen);
        bottomRight.setLayout(new GridLayout(1,2));
        bottomRight.setBorder(new EmptyBorder(10, 30, 30, 30));


        bottomButtons.add(fifty);
        bottomButtons.add(twenty);
        bottomButtons.add(ten);


        bottomRight.add(currentBetConstant);
        bottomRight.add(currentBet);
        
        bottom.add(bottomButtons);
        bottom.add(bottomRight);
        this.add(top);
        this.add(bottom);
    }
}