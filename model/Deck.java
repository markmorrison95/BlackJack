package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

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

    public void shuffleDeck(){
        Collections.shuffle(this);
    }
    public boolean refillTime(){
        return(this.size() < 6);
    }
}