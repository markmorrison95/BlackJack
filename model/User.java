package model;

import java.util.ArrayList;
/**
 * Player
 */
public class User extends Player{
    private static int nextIdNumber = 1;
    private int money;
    public User(){
        super(nextIdNumber);
        nextIdNumber++;
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