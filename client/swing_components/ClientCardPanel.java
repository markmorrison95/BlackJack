package client.swing_components;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

/**
 * CardPanel
 */
public class ClientCardPanel extends JPanel {
    private JPanel cardOne, cardTwo, cardThree, cardFour, cardFive;
    private JButton hit, stick;

    public ClientCardPanel(){
        this.setBackground(new Color(0, 102, 0));
        this.setBorder(new EmptyBorder(0, 30, 0, 30));
        GridLayout layout = new GridLayout(1,6);
        layout.setHgap(25);
        this.setLayout(layout);
        hit = new JButton("Hit");
        hit.setEnabled(false);
        stick = new JButton("Stick");
        stick.setEnabled(false);
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
        this.add(hit);
        this.add(cardOne);
        this.add(cardTwo);
        this.add(cardThree);
        this.add(cardFour); 
        this.add(cardFive);
        this.add(stick);
        
    }
    public JButton getHitButton() {
        return this.hit;
    }

    public JButton getStickButton(){
        return this.stick;
    }

    public JPanel[] getUserCardPanels(){
        JPanel[] pCards = new JPanel[5];
        pCards[0] = cardOne;
        pCards[1] = cardTwo;
        pCards[2] = cardThree;
        pCards[3] = cardFour;
        pCards[4] = cardFive;
        return pCards;
    }
}