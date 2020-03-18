package model;

/**
 * StickOrHit
 */
    public class StickOrHit {
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