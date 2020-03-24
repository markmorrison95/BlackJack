package model.enums;

/**
 * defines the suit of each cards and assigns a numerical value to represent
 */
public enum Suit {
    SPADES(1), CLUBS(2), DIAMONDS(3), HEARTS(4);
 
    private Integer suitValue;
 
    public final Integer getSuitValue() {
        return suitValue;
  }
 
    Suit(final int sv) {
        suitValue = sv;
    }
 
    public Integer SuitValue() {
        return suitValue;
 
    }

}