package model;

import java.util.ArrayList;

/**
 * Deck
 */
public class Deck extends ArrayList<Card>{

    public Deck(){
    }

    public void addCard(Card card){
        this.add(card);
    }

    public Card getAndRemoveCard(){
        Card top = this.get(0);
        this.remove(0);
        return top;
    }
}