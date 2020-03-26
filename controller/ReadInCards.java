package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Card;

import model.enums.CardValue;
import model.enums.Suit;
import model.Deck;

/**
 * ReadInCards
 */
public class ReadInCards {

    public static Deck readInCards() {
        /**
         * reads in the deck of cards file and creates a new card object with each line
         * then adds these to the mainDeck
         */
        Deck deck= new Deck();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("TwoDecksOfCards.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String suit = scanner.next();
            String value = scanner.next();
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            deck.add(new Card(Suit.valueOf(suit), CardValue.valueOf(value)));
        }
        scanner.close();
        return deck;
    }
}