package client.swing_components;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

/**
 * clientCardPanel
 * 
 * this is the middle panel which displays the users card and the buttons for hit and stick
 * just creates the layout and buttons and allows access to the buttons 
 * 
 * returns the panels where the cards will be displayed as an array list of JPanels
 */
public class ClientCardPanel extends JPanel {
    private JPanel cardOne, cardTwo, cardThree, cardFour, cardFive, cardSix, cardSeven;
    private JButton hit, stick;

    public ClientCardPanel(){
        this.setBackground(new Color(0, 102, 0));
        this.setBorder(new EmptyBorder(0, 30, 0, 30));
        GridLayout layout = new GridLayout(1,9);
        layout.setHgap(10);
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
        cardSix = new JPanel();
        cardSix.setLayout(new GridLayout());
        cardSix.setVisible(false);
        cardSeven = new JPanel();
        cardSeven.setLayout(new GridLayout());
        cardSeven.setVisible(false);
        this.add(hit);
        this.add(cardOne);
        this.add(cardTwo);
        this.add(cardThree);
        this.add(cardFour); 
        this.add(cardFive);
        this.add(cardSix);
        this.add(cardSeven);
        this.add(stick);
        
    }
    public JButton getHitButton() {
        return this.hit;
    }

    public JButton getStickButton(){
        return this.stick;
    }

    public JPanel[] getUserCardPanels(){
        JPanel[] pCards = new JPanel[7];
        pCards[0] = this.cardOne;
        pCards[1] = this.cardTwo;
        pCards[2] = this.cardThree;
        pCards[3] = this.cardFour;
        pCards[4] = this.cardFive;
        pCards[5] = this.cardSix;
        pCards[6] = this.cardSeven;
        return pCards;
    }
}