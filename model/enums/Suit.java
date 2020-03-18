package model.enums;


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