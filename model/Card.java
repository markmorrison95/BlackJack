package model;

/**
 * Card
 */
public class Card implements Comparable<Card> {
    private CardRank rank;
    private Suit suit;
    public Card(CardRank rank, Suit suit){
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public int compareTo(Card o) {
        return this.rank.cardValue().compareTo(o.rank.cardValue());
    }

    public CardRank getCardRank(){
        return this.rank;
    }
    public Suit getCardSuit(){
        return this.suit;
    }

}