package model;

import java.io.Serializable;

/**
 * Bet
 */
public class Bet implements Serializable{
    private int betAmount, playerID;
    public Bet(int playerID, int betAmount){
        this.playerID = playerID;
        this.betAmount = betAmount;
    }
}