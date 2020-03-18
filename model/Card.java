package model;

import java.io.Serializable;

import model.enums.*;

/**
 * Card
 */
public class Card implements Comparable<Card>, Serializable{
    private CardValue rank;
    private Suit suit;
    public Card(Suit suit, CardValue rank){
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public int compareTo(Card o) {
        return this.rank.cardValue().compareTo(o.rank.cardValue());
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