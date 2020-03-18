package client.swing_components;

import javax.swing.*;


import model.enums.*;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.GridLayout;

/**
 * CardCreator
 */
public class CardPanel extends JPanel {

    public CardPanel(){
        this.setLayout(new GridLayout());
    }

    public CardPanel(CardValue cv, Suit s) {
        this.setLayout(new GridLayout(3, 1));
        GridLayout layout = new GridLayout(1, 2);
        layout.setHgap(10);
        JPanel top = new JPanel();
        top.setLayout(layout);
        JPanel middle = new JPanel();
        JPanel bottom = new JPanel();
        bottom.setLayout(layout);
        ImagePanel iTop = new ImagePanel(s);
        JPanel numberPanelTop = new JPanel();
        JLabel numberLabelTop = new JLabel(cv.toString());
        numberPanelTop.add(numberLabelTop);
        ImagePanel ipBottom = new ImagePanel(s);
        JPanel numberPanelBottom = new JPanel();
        JLabel numberLabelBottom = new JLabel(cv.toString());
        numberPanelBottom.add(numberLabelBottom);
        top.add(iTop);
        top.add(numberPanelTop);
        bottom.add(numberPanelBottom);
        bottom.add(ipBottom);



        this.add(top);
        this.add(middle);
        this.add(bottom);
    }

    private class ImagePanel extends JPanel {

        private BufferedImage image;
        private String imagePath;
        public ImagePanel(Suit suit) {
            if(suit.SuitValue() == 1){
                    imagePath = "images/spades.png";
            }
            if(suit.SuitValue() == 2){
                imagePath = "images/clubs.png";
            }
            if(suit.SuitValue() == 3){
                imagePath = "images/diamonds.png";
            }
            if(suit.SuitValue() == 4){
                imagePath = "images/hearts.png";
            }
           try {                
              image = ImageIO.read(new File(imagePath));
           } catch (IOException ex) {
                ex.printStackTrace();
           }
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);          
        }
    
    }
    public static void main(String[] args) {
        JFrame j = new JFrame();
        j.setDefaultCloseOperation(2);
        CardPanel c = new CardPanel(CardValue.ACE, Suit.SPADES);
        j.add(c);
        j.pack();
        j.setVisible(true);
    }
}
