package model;

import java.io.Serializable;

/**
 * Bank
 */
public class Bank implements Serializable{
    private int noBets;
    private int money;

    public Bank(){
        noBets = 0;
        money = 0;
    }

    public void addMoney(int amount){
        money += amount;
        noBets++;
    }
    public int getNoBets() {
        return this.noBets;
    }
    public void resetNoBets(){
        this.noBets = 0;
    }
    
    public int getBalance(){
        return money;
    }

    public int getAndRemoveMoney(){
        int all = money;
        money = 0;
        return all;
    }
}