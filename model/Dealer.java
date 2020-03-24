package model;

/**
 * Dealer extends the player class and sets there ID to 0
 * and there balance to 0 as they dont place bets
 */
public class Dealer extends Player {
    public Dealer() {
        super(0,0);
    }
}