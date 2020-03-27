package model;
import java.util.ArrayList;

/**
 * Player extends ArrayList as this is the main function of this class, to hold
 * a list of the players cards. Each player also has an ID number a current balance and 
 * a current bet amount
 */
public class Player extends ArrayList<Card>{
    private final int ID;
    private int balance;
    private int currentBet;
    public Player(int ID, int money){
        this.ID = ID;
        this.balance = money;
    }

    public int getID(){
        return this.ID;
    }

    public void resetBalance(){
        this.balance = 200;
    }
    public void blackjackWin(){
        // if natural blackjack is returned 2.5 times there bet
        balance += (currentBet + (currentBet*1.5));
        currentBet = 0;
    }

    public void win(){
        // if wnd of round win gets 2 times there bet
        balance += (currentBet*2);
        currentBet = 0;
    }

    public void draw(){
        //if draw is returned there original bet
        balance += currentBet;
        currentBet = 0;
    }
    public void lose(){
        // if a loss they lose there bet
        currentBet = 0;
    }

    public ArrayList<Card> getAndRemoveAllCards(){
        /**
         * empties the players hand of cards and returns all the cards
         */
        ArrayList<Card> currentHand = new ArrayList<>();
        currentHand.addAll(this);
        this.clear();
        return currentHand;
    }
    public boolean isBust(){
        // says the player has a score over 21 and is bust
        return getCurrentScore() > 21;
    }

    public int getCurrentScore(){
        /**
         * totals up all the cards in the players current hand
         */
        int currentScore = 0;
        for(Card c : this){
            currentScore += c.getCardRank().cardValue();
        }
        return currentScore;
    }
    public int getBalance(){
        return this.balance;
    }
    
    public void makeBet(int amount){
        /**
         * removes the bet amount from the players current balance and adds
         * it to the currentBet
         */
        balance -= amount;
        currentBet = amount;
    }
}