package model.enums;

	/**
	 * enums representing the value of the cards. Makes it easy to read in cards from a text file
	 * also defines how to display each cards for when adding to the gui
	 */

    public enum CardValue {
		TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), 
		TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);
	 
        private Integer rankValue;
        	 
		CardValue(final int rankValue) {
		    this.rankValue = rankValue;
		}
	 
		public Integer cardValue() {
		    return rankValue;
	 
		}
		public String toString(){
			if(this.equals(ACE)){
				return "A";
			}
			if(this.equals(KING)){
				return "K";
			}
			if(this.equals(QUEEN)){
				return "Q";
			}
			if(this.equals(JACK)){
				return "J";
			}
			else return "" + rankValue;
		}
 
    }