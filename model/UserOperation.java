package model;

import java.io.Serializable;

/**
 * Bet object which contains a players ID and a bet amount
 * this is used to pass the best between the client and the server
 */
public class UserOperation implements Serializable{
    private final int number, playerID;
    public UserOperation(int playerID, int number){
        this.playerID = playerID;
        this.number = number;
    }

    public int getUserOperation(){
        return this.number;
    }
    public int getID(){
        return this.playerID;
    }
}