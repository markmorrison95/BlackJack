package model;
import java.util.ArrayList;

/**
 * playerAbstract
 */
public class Player extends ArrayList<Card>{
    private int ID;
    private int money;
    private int currentBet;
    public Player(int ID, int money){
        this.ID = ID;
        this.money = money;
    }

    public int getID(){
        return this.ID;
    }
    public void blackjackWin(){
        money += (currentBet + (currentBet*1.5));
        currentBet = 0;
    }

    public void win(){
        money += (currentBet*2);
        currentBet = 0;
    }

    public void draw(){
        money += currentBet;
        currentBet = 0;
    }
    public void lose(){
        currentBet = 0;
    }

    public ArrayList<Card> getAndRemoveAllCards(){
        ArrayList<Card> currentHand = new ArrayList<>();
        currentHand.addAll(this);
        this.clear();
        return currentHand;
    }
    public boolean isBust(){
        return getCurrentScore() > 21;
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
    
    public void makeBet(int amount){
        money -= amount;
        currentBet = amount;
    }

    public void addMoney(int amount){
        money += amount;
    }
    
}