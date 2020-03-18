package model;

import java.io.Serializable;

/**
 * StickOrHit
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