/* Adam Lopes
 * Professor Kanemoto
 * CPSC 39
 * 12-6-2024
*/



// Represents a single card
package com.texasHoldem.game;


//author- ChatGPT
//Date 11/30/2024
//ChatGpt
//JavaSE17
//AI generated code
//https://chatgpt.com/c/673bb432-3f04-8009-ad98-11bfa636cca1
public class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
