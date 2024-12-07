/* Adam Lopes
 * Professor Kanemoto
 * CPSC 39
 * 12-6-2024
*/



package com.texasHoldem.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;




//author- ChatGPT
//Date 11/30/2024
//ChatGpt
//JavaSE17
//AI generated code
//https://chatgpt.com/c/673bb432-3f04-8009-ad98-11bfa636cca1
class Deck {
    private LinkedList<Card> cards;
 // Represents a deck of cards
    public Deck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        cards = new LinkedList<>();

        // Populate the deck
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }

        shuffleDeck();
    }

    // Shuffle the deck
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }
      
    // Draw a card (remove the first card)
    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException("Deck is empty!");
        }
        return cards.removeFirst(); // remove from the head of the list.
    }

    
}// end class
