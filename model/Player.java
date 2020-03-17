package model;

import java.util.ArrayList;

/**
 * Player
 */
public class Player {
    private static int nextIdNumber = 0;
    private int ID;
    private ArrayList<Card> handOfCards;
    private int money;
    public Player(){
        ID = nextIdNumber;
        nextIdNumber++;
        handOfCards = new ArrayList<>();
        money = 200;

    }
    public ArrayList<Card> getHand(){
        return this.handOfCards;
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
        handOfCards.add(card);
    }
    public ArrayList<Card> getAndRemoveAllCards(){
        ArrayList<Card>  currentHand = handOfCards;
        handOfCards = new ArrayList<>();
        return currentHand;
    }
}