package model;
import java.util.ArrayList;

/**
 * playerAbstract
 */
public class Player extends ArrayList<Card>{
    private int ID;
    public Player(int ID){
        this.ID = ID;
    }

    public int getID(){
        return this.ID;
    }

    public ArrayList<Card> getAndRemoveAllCards(){
        ArrayList<Card>  currentHand = this;
        this.clear();
        return currentHand;
    }

    public int getCurrentScore(){
        int currentScore = 0;
        for(Card c : this){
            currentScore += c.getCardRank().cardValue();
        }
        return currentScore;
    }
    
}