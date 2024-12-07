/* Adam Lopes
 * Professor Kanemoto
 * CPSC 39
 * 12-6-2024
*/
package com.texasHoldem.game;
import java.util.*;

// Main Class
public class TexasHoldem {
    public static void main(String[] args) {
        PokerGame game = new PokerGame();
        
        Scanner scanner = new Scanner(System.in);

        // Add players
        game.addPlayer(new Player("Player 1", 100));
        game.addPlayer(new Player("Player 2", 100));

        // Pre-Flop
        game.dealHands();
        game.showPlayerHands();
        game.bettingRound();

        // Flop
        game.dealCommunityCards(3);
        game.showCommunityCards();
        game.bettingRound();

        // Turn
        game.dealCommunityCards(1);
        game.showCommunityCards();
        game.bettingRound();

        // River
        game.dealCommunityCards(1);
        game.showCommunityCards();
        game.bettingRound();

        game.showPlayerHands();
        game.showCommunityCards();
        game.determineWinner();
    }//end main
}// end class
