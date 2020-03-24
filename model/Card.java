package model;

import java.io.Serializable;

import model.enums.*;

/**
 * Card uses enums to represent the suit of the card and the rank of the card
 */
public class Card implements Serializable{
    private CardValue rank;
    private Suit suit;
    public Card(Suit suit, CardValue rank){
        this.suit = suit;
        this.rank = rank;
    }

    public CardValue getCardRank(){
        return this.rank;
    }
    public Suit getCardSuit(){
        return this.suit;
    }
    public String toString(){
        return "" + suit.name() + rank.cardValue();
    }

}