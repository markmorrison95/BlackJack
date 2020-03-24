package model;

/**
 * User extends the player class and sets there account balance to 200
 * takes an int ID for the constructor to allow them to be uniquely identified
 */
public class User extends Player{
    public User(int ID){
        super(ID, 50);
    }
}