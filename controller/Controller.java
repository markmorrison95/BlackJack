package controller;
import java.util.ArrayList;
import model.*;
import server.GameServer;

/**
 * Controller
 */
public class Controller {

    GameStats gameStats;
    Deck mainDeck, usedDeck;
    Dealer dealer;
    GameServer gameServer;

    public Controller() {
        mainDeck = new Deck();
        usedDeck = new Deck();
        gameStats = new GameStats(mainDeck);
        mainDeck.addAll(ReadInCards.readInCards());
        mainDeck.shuffleDeck();
        dealer = new Dealer();
        gameStats.addPlayer(dealer);
        Thread t = new Thread(gameServer = new GameServer(this, gameStats));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resetGame() {
        /**
         * when one player is left and has run out of money it will reset the game
         * finds the one player left and resets their balance. Sets the reset label 
         * in game stats to true and transmits the info to the client
         * 
         * refills the main deck of cards and shuffles
         * changes the resetGame boolean in gameStats back to false
         * and goes start new round
         * 
         */
        gameStats.resetGame(true);
        for (Player p : gameStats.values()) {
            if (p.getID() != 0) {
                p.resetBalance();
            }
        }
        gameServer.transmitStatsToAll();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        refillDeck();
        mainDeck.shuffleDeck();
        gameStats.resetGame(false);
        nextRound();
    }

    public void placeBet(UserOperation bet) {
        /**
         * takes a bet object as arguments and gets the player with the
         * correspondin ID and makes the bet with the amount 
         *
         * if the number of bets that has been made is the same as the amount of
         * people in the gameStats hashmap -1 then all the players have made a bet
         * and the cards can be dealt
         */
        Player p = gameStats.get(bet.getID());
        p.makeBet(bet.getUserOperation());
        gameStats.betMade();
        if (gameStats.getNoBets() == (gameStats.size() - 1)) {
            gameStats.allBetsReceived();
            dealCards();
        }

    }

    public void hitCards(UserOperation userOp) {
        /**
         * checks if the deck of cards needs to be refilled. 
         * adds a card to the hand of the player ID in the object
         * then transmits the updated info to the clients
         */
        if (mainDeck.refillTime()) {
            refillDeck();
        }
        gameStats.get(userOp.getID()).add(mainDeck.getAndRemoveCard());
        gameServer.transmitStatsToAll();
    }

    public void stickCards() {
        /**
         * checks if the player ID is the highest one in the map and if so
         * must be the last player to go and can move to the dealers round
         * if not increases the activePlayer so the next user can go
         */
        if (gameStats.getActivePlayer() == gameStats.getMaxUserID()) {
            dealerRound();
        } else {
            gameStats.increaseActivePlayer();
            gameServer.transmitStatsToAll();
        }
    }

    public boolean blackjackWinnerCheck() {
        /**
         * checks for a player who has 21, this if called after the first round is dealt
         * if a player or players have 21 they automatically win
         */
        boolean isBlackJackWin = false;
        for (Player p : gameStats.values()) {
            if (p.getCurrentScore() == 21) {
                p.blackjackWin();
                gameStats.addOneWinner(p);
                isBlackJackWin = true;
            }
        }
        return isBlackJackWin;
    }



    public void winCheck() {
        /**
         * checks for players that have 21 first and adds them to an arraylist if there
         * is no one with 21 then will find the score or equal scores that are closest
         * to 21. these need to be under 21 to count
         * 
         * then deals with the bets have been placed depending whether they have
         * won lost or drawn
         */
        ArrayList<Player> winners = new ArrayList<>();
        for (Player p : gameStats.values()) {
            if (p.getCurrentScore() == 21) {
                winners.add(p);
            }
        }
        if (winners.size() == 0) {
            for (Player p : gameStats.values()) {
                if (p.getCurrentScore() < 21 && winners.size() == 0) {
                    winners.add(p);
                } else if (p.getCurrentScore() < 21 && winners.size() > 0) {
                    if (p.getCurrentScore() > winners.get(0).getCurrentScore()) {
                        winners.clear();
                        winners.add(p);
                    } else if (p.getCurrentScore() == winners.get(0).getCurrentScore()) {
                        winners.add(p);
                    }
                }
            }
        }
        gameStats.addWinners(winners);
        if (winners.size() == 0) {
            for (Player p : gameStats.values()) {
                p.lose();
            }
        }

        else if (winners.size() == 1) {
            for (Player p : gameStats.values()) {
                if (p.getID() == winners.get(0).getID()) {
                    p.win();
                } else {
                    p.lose();
                }
            }
        } else if (winners.size() > 1) {
            boolean b;
            for (Player p : gameStats.values()) {
                b = false;
                for (Player pW : winners) {
                    if (p.getID() == pW.getID()) {
                        p.draw();
                        b = true;
                    }
                }
                if(!b){
                    p.lose();
                }
            }
        }
    }

    public void addUser(int ID) {
        /**
         * adds a new user when they open a client and assigns them an ID
         */
        gameStats.addPlayer(new User(ID));
    }

    public void dealerRound() {
        /**
         * this plays the round automatically for the dealer. if under 17 they must hit and if 17 or over they must stick
         * sleeps for a second after each new card dealt 
         * the win check is then called because all players now have there final hands
         * transmits the winners and then moves to the next round
         */
        gameStats.setDealerActivePlayer();
        Player dealer = gameStats.get(0);
        while (dealer.getCurrentScore() < 17) {
            if (mainDeck.refillTime()) {
                refillDeck();
            }
            dealer.add(mainDeck.getAndRemoveCard());
            gameServer.transmitStatsToAll();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        winCheck();
        gameServer.transmitStatsToAll();
        nextRound();
    }

    public void nextRound() {
        /**
         * checks if any players have run out of money. 
         * sleeps for 3 seconds to allow users to read the info label with the round outcome
         * 
         * then resets the players hands and tells the gameStats object that its going back to betting
         * and sends this to all the clients
         */
        moneyCheck();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            gameStats.resetBettingRound();
            removeAllPlayersHands();
            gameServer.transmitStatsToAll();
        }
    }

    public void dealCards() {
        /**
         * deals out 2 cards to each player checks if deck is empty and if so adds in
         * the used cards and then continues
         */
        for (int i = 0; i < 2; i++) {
            for (Player p : gameStats.values()) {
                if (mainDeck.refillTime()) {
                    refillDeck();
                }
                p.add(mainDeck.getAndRemoveCard());
            }
        }
        boolean win = blackjackWinnerCheck();
        gameServer.transmitStatsToAll();
        if(win){nextRound();}
    }

    public void removeAllPlayersHands() {
        for (Player player : gameStats.values()) {
            usedDeck.addAll(player.getAndRemoveAllCards());
        }
    }

    public void moneyCheck() {
        /**
         * checks if any user has run out of money. They are then removed from the game.
         * if it is the last player who is out of money then it will rest the game.
         */
            ArrayList<Integer> losers = new ArrayList<>();
            for (Player p : gameStats.values()) {
                if (p.getBalance() <= 0 && p.getID() != 0) {
                    losers.add(p.getID());
                }
            }
            if(gameStats.size() == 2 && losers.size() == 1){
                resetGame();
            }else{
            for (Integer i : losers) {
                removePlayer(i);
            }
        }
    }

    public void removePlayer(int ID){
        /**
         * removes the player who's ID is passed
         * they are removed from the game stats object and also from the ClientRunner list in server
         */
        gameServer.removeClient(ID);
        gameStats.remove(ID);
    }

    public void refillDeck() {
        /**
         * adds the used cards back into the main deck and shuffles
         */
        mainDeck.addAll(usedDeck);
        usedDeck.clear();
        mainDeck.shuffleDeck();
    }

    public static void main(String[] args) {
        new Controller();
    }
}