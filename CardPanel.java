import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

/**
 * CardPanel
 */
public class CardPanel extends JPanel {
    private JPanel cardOne, cardTwo, cardThree, cardFour;
    private JButton hit, stick;

    public CardPanel(){
        this.setBackground(new Color(0, 102, 0));
        this.setBorder(new EmptyBorder(0, 30, 0, 30));
        GridLayout layout = new GridLayout(1,6);
        layout.setHgap(25);
        this.setLayout(layout);
        hit = new JButton("Hit");
        stick = new JButton("Stick");
        cardOne = new JPanel();
        cardTwo = new JPanel();
        cardThree = new JPanel();
        cardFour = new JPanel();
        this.add(hit);
        this.add(cardOne);
        this.add(cardTwo);
        this.add(cardThree);
        this.add(cardFour);
        this.add(stick);
        
    }
}