package model;

import java.io.Serializable;

/**
 * Bank
 */
public class Bank implements Serializable{

    private int money;

    public Bank(){
        money = 0;
    }

    public void addMoney(int amount){
        money += amount;
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