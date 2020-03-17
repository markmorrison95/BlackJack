package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import model.*;
import model.enums.CardRank;
import model.enums.Suit;

/**
 * Controller
 */
public class Controller {

    GameStats gameStats;
    Deck mainDeck, usedDeck;
    Dealer dealer;

    public Controller() {
        mainDeck = new Deck();
        gameStats = new GameStats(mainDeck);
        readInCards();
        mainDeck.shuffleDeck();
        dealer = new Dealer();
        gameStats.addPlayer(dealer);
    }

    public void addUser() {
        gameStats.addPlayer(new User());
    }

    public void dealCards(){
        HashMap<Integer, Player> players = gameStats.getPlayerMap();
        for(int i=0; i<2; i++){
            for(Entry<Integer, Player> player : players.entrySet()) {
                Player p = player.getValue();
                p.add(mainDeck.getAndRemoveCard());
            }
        }
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