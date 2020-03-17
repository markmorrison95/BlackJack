package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import model.*;
import model.enums.CardRank;
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

    public Controller() {
        mainDeck = new Deck();
        gameStats = new GameStats(mainDeck);
        readInCards();
        mainDeck.shuffleDeck();
        dealer = new Dealer();
        bank = new Bank();
        gameStats.addPlayer(dealer);
        dealCards();
        Thread t = new Thread(new GameServer(this, gameStats));
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
        gameStats.getPlayerMap().get(ID).add(mainDeck.getAndRemoveCard());
    }

    public void placeBet(int ID, int bet){
        User u = (User)gameStats.getPlayerMap().get(ID);
        u.removeMoney(bet);
        bank.addMoney(bet);

    }

    public void addUser(int ID) {
        gameStats.addPlayer(new User(ID));
    }

    public void dealCards(){
        /**
         * deals out 2 cards to each player
         * checks if deck is empty and if so adds in the used cards
         * and then continues
         */
        HashMap<Integer, Player> players = gameStats.getPlayerMap();
        for(int i=0; i<2; i++){
            for(Entry<Integer, Player> player : players.entrySet()) {
                if(mainDeck.refillTime()){
                    refillDeck();
                }
                Player p = player.getValue();
                p.add(mainDeck.getAndRemoveCard());
            }
        }
    }

    public void removeAllPlayersHands(){
        HashMap<Integer, Player> players = gameStats.getPlayerMap();
            for(Entry<Integer, Player> player : players.entrySet()) {
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
			mainDeck.addCard(new Card(Suit.valueOf(suit), CardRank.valueOf(value)));
		}
		scanner.close();
    }
    
    public static void main(String[] args) {
        new Controller();
    }
}