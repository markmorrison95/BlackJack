package model;

import java.io.Serializable;

/**
 * Bet object which contains a players ID and a bet amount
 * this is used to pass the best between the client and the server
 */
public class Bet implements Serializable{
    private int betAmount, playerID;
    public Bet(int playerID, int betAmount){
        this.playerID = playerID;
        this.betAmount = betAmount;
    }

    public int getBetAmount(){
        return this.betAmount;
    }
    public int getPlayerId(){
        return this.playerID;
    }
}