package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import model.*;
import model.enums.*;
import server.GameServer;

/**
 * Controller
 */
public class Controller {

    GameStats gameStats;
    Deck mainDeck, usedDeck;
    Dealer dealer;
    Bank bank;
    GameServer gameServer;

    public Controller() {
        mainDeck = new Deck();
        usedDeck = new Deck();
        gameStats = new GameStats(mainDeck);
        readInCards();
        readInCards();
        mainDeck.shuffleDeck();
        dealer = new Dealer();
        bank = new Bank();
        gameStats.addPlayer(dealer);
        Thread t = new Thread(gameServer = new GameServer(this, gameStats));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void placeBet(Bet bet) {
        Player p = gameStats.get(bet.getPlayerId());
        p.makeBet(bet.getBetAmount());
        gameStats.betMade();
        if (gameStats.getNoBets() == (gameStats.size() - 1)) {
            gameStats.allBetsRecieved();
            dealCards();
        }

    }

    public void hitCards(StickOrHit s) {
        if (mainDeck.refillTime()) {
            refillDeck();
        }
        gameStats.get(s.getID()).add(mainDeck.getAndRemoveCard());
        gameServer.transmitStatsToAll();
    }

    public void stickCards() {
        if (gameStats.getActivePlayer() == (gameStats.getRoundSize() - 1)) {
            dealerRound();
        } else {
            gameStats.increaseActivePlayer();
            gameServer.transmitStatsToAll();
        }
    }

    public void blackjackWinnerCheck() {
        boolean isBlackJackWin = false;
        for (Player p : gameStats.values()) {
            if (p.getCurrentScore() == 21) {
                p.blackjackWin();
                isBlackJackWin = true;
            }
        }
        if (isBlackJackWin == true) {
            gameServer.transmitFirstRoundWinner(true);
            nextRound();
        } else {
            gameServer.transmitFirstRoundWinner(false);
        }
    }

    public void winCheck() {
        /**
         * checks for players that have 21 first and adds them to an arraylist if there
         * is no one with 21 then will find the score or equal scores that are closest
         * to 21. these need to be under 21 ro count
         */
        ArrayList<Player> winner = new ArrayList<>();
        for (Player p : gameStats.values()) {
            if (p.getCurrentScore() == 21) {
                winner.add(p);
            }
        }
        if (winner.size() == 0) {
            for (Player p : gameStats.values()) {
                if (p.getCurrentScore() < 21 && winner.size() == 0) {
                    winner.add(p);
                } else if (p.getCurrentScore() < 21 && winner.size() > 0) {
                    if (p.getCurrentScore() > winner.get(0).getCurrentScore()) {
                        winner.clear();
                        winner.add(p);
                    } else if (p.getCurrentScore() == winner.get(0).getCurrentScore()) {
                        winner.add(p);
                    }
                }
            }
        }
        if (winner.size() == 0) {
            for (Player p : gameStats.values()) {
                p.lose();
            }
        }

        else if (winner.size() == 1) {
            for (Player p : gameStats.values()) {
                if (p.getID() == winner.get(0).getID()) {
                    p.win();
                } else {
                    p.lose();
                }
            }
        }
        else if (winner.size() > 0) {
            for (Player p : gameStats.values()) {
                for (Player pW : winner) {
                    if (p.getID() ==  pW.getID()){
                        p.draw();
                    } else {
                        p.lose();
                    }
                }
            }
        }
    }

    public void addUser(int ID) {
        gameStats.addPlayer(new User(ID));
    }

    public void dealerRound() {
        gameStats.setDealerActivePlayer();
        Player dealer = gameStats.get(0);
        while (dealer.getCurrentScore() < 17) {
            if (mainDeck.refillTime()) {
                refillDeck();
            }
            dealer.add(mainDeck.getAndRemoveCard());
            gameServer.transmitStatsToAll();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        winCheck();
        gameServer.transmitStatsToAll();
        nextRound();
    }

    public void nextRound() {
        try {
            Thread.sleep(2000);
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
            for (int x = 0; x < gameStats.getRoundSize(); x++) {
                if (mainDeck.refillTime()) {
                    refillDeck();
                }
                gameStats.get(x).add(mainDeck.getAndRemoveCard());
            }
        }
        gameServer.transmitStatsToAll();
        blackjackWinnerCheck();
    }

    public void removeAllPlayersHands() {
        for (Player player : gameStats.values()) {
            usedDeck.addAll(player.getAndRemoveAllCards());
            System.out.println(usedDeck.size());
        }
    }

    public void refillDeck() {
        /**
         * adds the used cards back into the main deck and shuffles
         */
        System.out.println(usedDeck.size());
        mainDeck.addAll(usedDeck);
        usedDeck.clear();
        mainDeck.shuffleDeck();
    }

    public void readInCards() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("deckOfCards.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String suit = scanner.next();
            String value = scanner.next();
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            mainDeck.addCard(new Card(Suit.valueOf(suit), CardValue.valueOf(value)));
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new Controller();
    }
}