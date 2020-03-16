package model;

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
    public String getSuitString(){
        if(this.suitValue == 1){
            return "Spades";
        }
        if(this.suitValue == 2){
            return "Clubs";
        }
        if(this.suitValue == 3){
            return "Diamonds";
        }
        if(this.suitValue == 4){
            return "Hearts";
        }
        else return null;
        
    }

}