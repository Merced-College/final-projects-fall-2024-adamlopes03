// Manages the poker game
package com.texasHoldem.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;


class PokerGame {
    private Deck deck;
    private HashMap<Player, List<Card>> playerHands;
    private LinkedList<Player> playerTurnOrder = new LinkedList<>();
    private List<Card> communityCards = new ArrayList<>();
    private Queue<Player> playerQueue;
    private int pot = 0;
    private int currentBet; // Initialize to 0 at the start of each round.

    public PokerGame() {
        deck = new Deck();
        playerHands = new HashMap<>();
        communityCards = new ArrayList<>();
        //startGame();
    }

    // Add a player to the game
    public void addPlayer(Player player) {
        playerHands.put(player, new ArrayList<>()); // makes player have an empty hand initially
        playerTurnOrder.add(player); // adds player to the turn queue
    }
    
    //public void startGame() {
        // Pre-Flop
        //dealHands();
        //bettingRound();

        // Flop
        //dealCommunityCards(3);
        //bettingRound();

        // Turn
        //dealCommunityCards(1);
        //bettingRound();

        // River
        //dealCommunityCards(1);
        //bettingRound();

        //determineWinner();
  // }

    // Deal two cards to each player
    public void dealHands() {
        for (Player player : playerHands.keySet()) {
            List<Card> hand = playerHands.get(player);
            hand.add(deck.drawCard());
            hand.add(deck.drawCard());
        }
    }
    
    // Place a bet
    public void placeBet(Player player, int amount) {
        player.deductBalance(amount);
        pot += amount;
    }
    
 
    public void handleFold(Player player) {
        playerHands.remove(player); // Remove the player from the game.
    }

    public int getPot() {
        return pot;
    }
    
    public int getCurrentBet() {
        return currentBet;
    }

    // Deal community cards (flop, turn, river)
    public void dealCommunityCards(int count) {
        for (int i = 0; i < count; i++) {
            communityCards.add(deck.drawCard());
        }
    }

    // Display the hands of all players
    public void showPlayerHands() {
        for (Map.Entry<Player, List<Card>> entry : playerHands.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    
    public void showPlayer1Hand() {
    	Player firstPlayer = playerTurnOrder.get(0); // Assuming playerTurnOrder is a List<Player>.
        List<Card> firstPlayerHand = playerHands.get(firstPlayer); // playerHands is a Map<Player, List<Card>>.

        System.out.println(firstPlayer.getName() + "'s Hand: " + firstPlayerHand);
    }
    
    public List<Card> getPlayerHand(Player player) {
        if (playerHands.containsKey(player)) {
            return new ArrayList<>(playerHands.get(player)); // Return a copy to prevent modification.
        } else {
            throw new IllegalArgumentException("Player not found!");
        }
    }

    // Display the community cards
    public void showCommunityCards() {
        System.out.println("Community Cards: " + communityCards);
    }
    
    // returns a copy of community Cards
    public List<Card> getCommunityCards() {
    	return new ArrayList<>(communityCards);
    }
    
    public Queue<Player> getPlayerTurnOrder() {
    	return playerTurnOrder;
    }
    
    // Controls the betting round- ChatGPT
    public void bettingRound() {
        int currentBet = 0; // Current bet amount for the round.
        Set<Player> activePlayers = new HashSet<>(playerHands.keySet()); // Players still in the round.

        while (true) {
            boolean allPlayersMatched = true; // Flag to check if all players matched the current bet.

            for (Player player : new LinkedList<>(activePlayers)) {
            	System.out.println();
                System.out.println(player.getName() + ", it's your turn!");
                System.out.println("Your balance: " + player.getBalance());
                System.out.println("Current bet: " + currentBet);
                System.out.println("Pot: " + pot);
                System.out.println("1: Fold, 2: Call, 3: Raise, 4: Check");

                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: // Fold
                        System.out.println(player.getName() + " folds.");
                        activePlayers.remove(player);
                        break;

                    case 2: // Call
                        int callAmount = currentBet; // Amount to match.
                        if (player.getBalance() < callAmount) {
                            System.out.println("Insufficient balance to call.");
                            activePlayers.remove(player); // Force player to fold.
                        } else {
                            player.deductBalance(callAmount);
                            pot += callAmount;
                            System.out.println(player.getName() + " calls with " + callAmount);
                        }
                        break;

                    case 3: // Raise
                        System.out.println("Enter raise amount:");
                        int raiseAmount = scanner.nextInt();
                        if (player.getBalance() < (currentBet + raiseAmount)) {
                            System.out.println("Insufficient balance to raise.");
                        } else {
                            currentBet += raiseAmount; // Update current bet.
                            player.deductBalance(currentBet);
                            pot += currentBet;
                            System.out.println(player.getName() + " raises to " + currentBet);
                            allPlayersMatched = false; // A raise means players must re-match.
                        }
                        break;

                    case 4: // Check
                        if (currentBet > 0) {
                            System.out.println("You cannot check; you must call or raise.");
                            activePlayers.remove(player); // Force player to fold.
                        } else {
                            System.out.println(player.getName() + " checks.");
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                // If only one player remains, end the round.
                if (activePlayers.size() == 1) {
                    System.out.println(activePlayers.iterator().next().getName() + " wins by default!");
                    return;
                }
            }

            // Check if all players have matched the current bet.
            if (allPlayersMatched) {
                System.out.println("Betting round complete.");
                break;
            }
        }
    }// End of bettingRound
    
  // Breaks a Tie based of who has the high card-modify in future to compare rank of hands in tiebreaker
  //author- ChatGPT
  //Date 12/5/2024
  // ChatGpt
  //JavaSE17
  //AI generated code
  //https://chatgpt.com/c/673bb432-3f04-8009-ad98-11bfa636cca1
    public void BreakTie(List<Player> tiedPlayers) {
        Player winner = null;
        Card bestHighCard = null;

        for (Player player : tiedPlayers) {
            List<Card> playerHand = playerHands.get(player);
            Card highCard = getHighCard(playerHand, communityCards);

            if (winner == null || compareCards(highCard, bestHighCard) > 0) {
                winner = player;
                bestHighCard = highCard;
            }
        }

        if (winner != null) {
            System.out.println("Winner: " + winner.getName() + " with high card " + bestHighCard);
            winner.addWinnings(pot);
            pot = 0;
        }
    }

    

  /*author- ChatGPT
   Date 12/5/2024
   ChatGpt
   JavaSE17
   AI generated code
   https://chatgpt.com/c/673bb432-3f04-8009-ad98-11bfa636cca1
  */
    private Card getHighCard(List<Card> hand, List<Card> communityCards) {
	    List<Card> allCards = new ArrayList<>(hand);
	    allCards.addAll(communityCards);

	    return allCards.stream()
	            .max((c1, c2) -> Integer.compare(HandEvaluator.rankToValue.get(c1.getRank()), HandEvaluator.rankToValue.get(c2.getRank())))
	            .orElse(null); // Return the highest card or null if the list is empty.
		}
		
		private int compareCards(Card card1, Card card2) {
		    if (card1 == null && card2 == null) return 0;
		    if (card1 == null) return -1;
		    if (card2 == null) return 1;

		    return Integer.compare(HandEvaluator.rankToValue.get(card1.getRank()), HandEvaluator.rankToValue.get(card2.getRank()));
		}
    
    

        // determines winner of the hand
		public void determineWinner() {
		    List<Player> topPlayers = new ArrayList<>();
		    int bestScore = 0;

		    for (Player player : playerHands.keySet()) {
		        int score = HandEvaluator.evaluateHand(playerHands.get(player), communityCards);

		        if (score > bestScore) {
		            bestScore = score;
		            topPlayers.clear(); // Clear previous top players
		            topPlayers.add(player);
		        } else if (score == bestScore) {
		            topPlayers.add(player); // Add player to the list of tied players
		        }
		    }

		    if (topPlayers.size() == 1) {
		        Player winner = topPlayers.get(0);
		        System.out.println("Winner: " + winner.getName() + " with a score of " + bestScore);
		        winner.addWinnings(pot);
		        pot = 0;
		    } else {
		        System.out.println("Tie detected. Breaking tie...");
		        BreakTie(topPlayers);
		    }
		}

    
    
    
    

    
}// end class