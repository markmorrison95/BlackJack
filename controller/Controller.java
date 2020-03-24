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
                gameStats.addAwinner(p);
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
         * to 21. these need to be under 21 to count
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
        } else if (winners.size() > 0) {
            for (Player p : gameStats.values()) {
                for (Player pW : winners) {
                    if (p.getID() == pW.getID()) {
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        nextRound();
    }

    public void nextRound() {
        moneyCheck();
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
        }
    }

    public void moneyCheck(){
        ArrayList<Integer> losers = new ArrayList<>();
        for(Player p:gameStats.values()){
            if(p.getBalance() <= 0 && p.getID() != 0){
                losers.add(p.getID());
            }
        }for(Integer i:losers){
            removePlayer(i);
        }
    }

    public void removePlayer(int ID){
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