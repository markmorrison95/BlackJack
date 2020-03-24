package model;

import java.io.Serializable;

/**
 * StickOrHit is used to pass info from the client to the server.
 * allows an operation and the players ID to be passed
 */
    public class StickOrHit implements Serializable {
    private int ID;
    private int operation;

    public StickOrHit(int ID, int operation){
        this.ID = ID;
        this.operation = operation;
    }

    public int getID() {
        return this.ID;
    }

    public int getOperation() {
        return this.operation;
    }

}