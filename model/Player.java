package model;
import java.util.ArrayList;

/**
 * playerAbstract
 */
public class Player extends ArrayList<Card>{
    private int ID;
    private int money;
    public Player(int ID, int money){
        this.ID = ID;
        this.money = money;
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
    public int getBalance(){
        return this.money;
    }
    
    public int removeMoney(int amount){
        money -= amount;
        return amount;
    }

    public void addMoney(int amount){
        money += amount;
    }
    
}