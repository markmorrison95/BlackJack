package model;

import java.util.ArrayList;
/**
 * Player
 */
public class User extends Player{
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