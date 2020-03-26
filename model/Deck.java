package model;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck stores a collection of cards. Is used for the main deck ans also for the used pile
 * of cards after each round. Allows shuffling of the cards.
 */
public class Deck extends ArrayList<Card>{

    public Deck(){
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
        /**
         * boolean that says the deck should be refilled if less than 6 cards
         */
        return(this.size() < 6);
    }
}