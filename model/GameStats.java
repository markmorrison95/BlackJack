package model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * GameStats
 */
public class GameStats implements Serializable{
    private int noCardsInDeck;
    private int activePlayer;
    private HashMap<Integer, Player> players;

    public GameStats(Deck mainDeck){
        noCardsInDeck = mainDeck.size();
        players = new HashMap<>();
        activePlayer = 0;
    }

    public HashMap<Integer, Player> getPlayerMap(){
        return this.players;
    }

    public void addPlayer(Player player){
        Integer ID = player.getID();
        players.put(ID, player);
    }

    public void setActivePlayer(int id){
        activePlayer = id;
    }
    public int getActivePlayer(){
        return this.activePlayer;
    }
    public int getNoCardsInDeck(){
        return this.noCardsInDeck;
    }
    
}