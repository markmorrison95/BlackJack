package model;

import java.util.ArrayList;
/**
 * Player
 */
public class Player extends ArrayList<Card>{
    private static int nextIdNumber = 0;
    private int ID;
    private int money;
    public Player(){
        ID = nextIdNumber;
        nextIdNumber++;
        money = 200;

    }
    public ArrayList<Card> getHand(){
        return this;
    }

    public int removeMoney(int amount){
        money -= amount;
        return amount;
    }

    public void addMoney(int amount){
        money += amount;
    }

    public int getID(){
        return this.ID;
    }

    public void addCard(Card card){
       this.add(card);
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