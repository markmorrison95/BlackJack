package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import model.*;
import model.enums.CardValue;
import model.enums.Suit;
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
        gameStats = new GameStats(mainDeck);
        readInCards();
        mainDeck.shuffleDeck();
        dealer = new Dealer();
        bank = new Bank();
        gameStats.addPlayer(dealer);
        Thread t = new Thread(gameServer = new GameServer(this, gameStats));
        t.start();
        try {
            t.join();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hitCards(int ID){
        if(mainDeck.refillTime()){
            refillDeck();
        }
        gameStats.get(ID).add(mainDeck.getAndRemoveCard());
    }

    public void placeBet(Bet bet){
        User u = (User)gameStats.get(bet.getPlayerId());
        bank.addMoney(u.removeMoney(bet.getBetAmount()));
        if(bank.getNoBets() == (gameStats.size() - 1)){
            bank.resetNoBets();
            gameStats.allBetsRecieved();
            dealCards();
        }

    }
    public void hitCards(StickOrHit s){
        if(mainDeck.refillTime()){
            refillDeck();
        }
        gameStats.get(s.getID()).add(mainDeck.getAndRemoveCard());
        gameServer.transmitStatsToAll();
    }


    public void stickCards(StickOrHit s){
        if(gameStats.getActivePlayer() == (gameStats.getRoundSize() -1)){
        dealerRound();
        }else{
        gameStats.increaseActivePlayer();
        gameServer.transmitStatsToAll();
        }
    }

    public void addUser(int ID) {
        gameStats.addPlayer(new User(ID));
    }

    public void dealerRound(){
        gameStats.setDealerActivePlayer();
        Player dealer = gameStats.get(0);
        while(dealer.getCurrentScore() < 18){
            if(mainDeck.refillTime()){
                refillDeck();
            }
            dealer.add(mainDeck.getAndRemoveCard());
            gameServer.transmitStatsToAll();
        }
    }

    public void dealCards(){
        /**
         * deals out 2 cards to each player
         * checks if deck is empty and if so adds in the used cards
         * and then continues
         */
        for(int i=0; i<2; i++){
            for(int x = 0; x < gameStats.getRoundSize(); x++ ) {
                if(mainDeck.refillTime()){
                    refillDeck();
                }
                gameStats.get(x).add(mainDeck.getAndRemoveCard());
            }
        }
        gameServer.transmitStatsToAll();
    }

    public void removeAllPlayersHands(){
            for(Entry<Integer, Player> player : gameStats.entrySet()) {
                usedDeck.addAll(player.getValue().getAndRemoveAllCards());
            }
    }

    public void refillDeck(){
        /**
         * adds the used cards back into the main deck and shuffles
         */
        mainDeck.addAll(usedDeck);
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
            if(scanner.hasNextLine()) {scanner.nextLine();}
			mainDeck.addCard(new Card(Suit.valueOf(suit), CardValue.valueOf(value)));
		}
		scanner.close();
    }
    
    public static void main(String[] args) {
        new Controller();
    }
}