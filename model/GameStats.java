package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * GameStats is used as the running info required to be sent to all the clients
 * this class extends HashMap so that each player object can be stored in an easily
 * accessible manner 
 */
public class GameStats extends HashMap<Integer, Player>{
    private int activePlayer;
    private int maxUserID, noBets;
    private boolean waitingForBets, gameReset;
    private ArrayList<Player> winners;

    public GameStats(Deck mainDeck){
        gameReset = false;
        noBets = 0;
        // initiates game with betting round
        waitingForBets = true;
        winners = new ArrayList<>();
    }

    public void addWinners(ArrayList<Player> w){
        // if more than one winner can a list of Players
        this.winners.addAll(w);
    }
    public void addOneWinner(Player p){
        // adds one list to the winners list
        this.winners.add(p);
    }
    public ArrayList<Player> getWinners(){
        return this.winners;
    }

    public void resetGame(boolean b){
        gameReset = b;
    }
    public boolean hasGameBeenReset(){
        return this.gameReset;
    }



    public int getNoBets() {
        return this.noBets;
    }
    public void betMade(){
        //signifies a bet has been made
        noBets++;
    }

    public void addPlayer(Player player){
        /**
         * adds a new player to the hashmap and maps
         * the key to there player ID
         */
        Integer ID = player.getID();
        this.put(ID, player);
    }


    public boolean isWaitingForBets(){
        //returns the current betting status
        return waitingForBets;
    }



    public int getActivePlayer(){
        return this.activePlayer;
    }

    public void increaseActivePlayer(){
        /**
         * increases the active player to the next player
         * does this by finding the current active player in the list and sets the next
         * player as active. Needs to do this because players can leave in the middle of the game
         * so may need to go from player 3 to player 6
         */
        boolean next = false;
        for(Player p: this.values()){
            if(next){
                this.activePlayer = p.getID();
                break;
            }
            if(p.getID() == this.activePlayer){
                next = true;
            }
        }
    }
    public void setDealerActivePlayer(){
        // sets the dealer who is always player 0 to be the active player
        activePlayer = 0;
    }

    public void allBetsReceived(){
        /**
         * sets the boolean to show that al bets have been received
         * sets the ma=xUserId as the highest value of player ID in the hashmap
         * then sets the active player as the first player after the dealer
         */
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


    public void resetBettingRound() {
    /**
     * this says that all bets have been received and sets the active player
     * to the first user in the hash map. Loops through so if players have left the game will ignore that number
     * resets the number of bets received to 0 and clears the list of winners
     */
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