package model;

import java.util.ArrayList;

/**
 * Player
 */
public class Player {
    private static int nextIdNumber = 0;
    private int ID;
    private ArrayList<Card> cards;
    private int money;
    public Player(){
        ID = nextIdNumber;
        nextIdNumber++;
        cards = new ArrayList<>();
        money = 200;

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
        cards.add(card);
    }
    public ArrayList<Card> getAndRemoveAllCards(){
        ArrayList<Card>  currentHand = cards;
        cards = new ArrayList<>();
        return currentHand;
    }
}