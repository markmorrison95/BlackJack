package model;

import java.util.HashMap;

/**
 * GameStats
 */
public class GameStats extends HashMap<Integer, Player>{
    private int noCardsInDeck;
    private int activePlayer;
    private int roundSize, noBets;
    private boolean waitingForBets;

    public GameStats(Deck mainDeck){
        noBets = 0;
        roundSize = 1;
        waitingForBets = true;
        noCardsInDeck = mainDeck.size();
        activePlayer = 1;
    }
    public int getNoBets() {
        return this.noBets;
    }
    public void betMade(){
        noBets++;
    }

    public void addPlayer(Player player){
        Integer ID = player.getID();
        this.put(ID, player);
    }
    public boolean isWaitingForBets(){
        return waitingForBets;
    }
    public int getActivePlayer(){
        return this.activePlayer;
    }
    public void setActivePlayer(int id){
        activePlayer = id;
    }
    public void increaseActivePlayer(){
        this.activePlayer++;
    }
    public void setDealerActivePlayer(){
        activePlayer = 0;
    }
    public void resetActivePlayer(){
        this.activePlayer = 1;
    }
    public int getNoCardsInDeck(){
        /**
         * signals if the deck has less than 6 cards left and the used cards
         * should be added back in and all cards re shuffled
         */
        return this.noCardsInDeck;
    }
    public void allBetsRecieved(){
        waitingForBets = false;
        final int s = this.size();
        roundSize = s;
    }
    public int getRoundSize(){
        return this.roundSize;
    }
    public void resetBettingRound() {
        waitingForBets = true;
        activePlayer = 1;
        noBets = 0;
    }
}