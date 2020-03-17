package model;

import java.io.Serializable;
/**
 * Player
 */
public class User extends Player implements Serializable{
    private int money;
    public User(int ID){
        super(ID);
        money = 200;
    }

    public int removeMoney(int amount){
        money -= amount;
        return amount;
    }

    public void addMoney(int amount){
        money += amount;
    }
}