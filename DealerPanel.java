import java.awt.Color;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

/**
 * cardPanel
 */
public class DealerPanel extends JPanel{
    private JPanel cardOne, cardTwo, cardThree, cardFour;

    public DealerPanel(){
        this.setBackground(new Color(0, 102, 0));
        this.setBorder(new EmptyBorder(0, 60, 30, 60));
        GridLayout layout = new GridLayout(1,4);
        layout.setHgap(25);
        this.setLayout(layout);
        cardOne = new JPanel();
        cardTwo = new JPanel();
        cardThree = new JPanel();
        cardFour = new JPanel();
        this.add(cardOne);
        this.add(cardTwo);
        this.add(cardThree);
        this.add(cardFour);
        
    }
}