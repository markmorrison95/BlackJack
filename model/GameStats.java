package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * GameStats
 */
public class GameStats extends HashMap<Integer, Player>{
    private int noCardsInDeck;
    private int activePlayer;
    private int maxUserID, noBets;
    private boolean waitingForBets;
    private ArrayList<Player> winners;

    public GameStats(Deck mainDeck){
        noBets = 0;
        maxUserID = 1;
        waitingForBets = true;
        noCardsInDeck = mainDeck.size();
        activePlayer = 1;
        winners = new ArrayList<>();
    }

    public void addWinners(ArrayList<Player> w){
        this.winners.addAll(w);
    }
    public void addAwinner(Player p){
        this.winners.add(p);
    }
    public ArrayList<Player> getWinners(){
        return this.winners;
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
        boolean next = false;
        for(Player p: this.values()){
            if(next){
                this.activePlayer = p.getID();
                System.out.println("active player: " + p.getID());
                break;
            }
            if(p.getID() == this.activePlayer){
                next = true;
            }
        }
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
        maxUserID = Collections.max(this.keySet());
        for(Player p: this.values()){
            if(p.getID() != 0){
                activePlayer = p.getID();
                break;
            }
        }
    }

    public int getMaxUserID(){
        return this.maxUserID;
    }

    /**
     * this says that all bets have been received and sets the active player
     * to the first user in the hash map. Loops through so if players have left the game will ignore that number
     * resets the number of bets received to 0 and clears the list of winners
     */
    public void resetBettingRound() {
        waitingForBets = true;
        for(Player p: this.values()){
            if(p.getID() != 0){
                activePlayer = p.getID();
                break;
            }
        }
        noBets = 0;
        winners.clear();
    }
}