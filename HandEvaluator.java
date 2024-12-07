//author- ChatGPT
//Date 12/5/2024
// ChatGpt
//JavaSE17
//AI generated code
//https://chatgpt.com/c/673bb432-3f04-8009-ad98-11bfa636cca1
package com.texasHoldem.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


public class HandEvaluator {
	
	public static Map<String, Integer> rankToValue = new HashMap<>();

	static {
	    rankToValue.put("2", 2);
	    rankToValue.put("3", 3);
	    rankToValue.put("4", 4);
	    rankToValue.put("5", 5);
	    rankToValue.put("6", 6);
	    rankToValue.put("7", 7);
	    rankToValue.put("8", 8);
	    rankToValue.put("9", 9);
	    rankToValue.put("10", 10);
	    rankToValue.put("Jack", 11);
	    rankToValue.put("Queen", 12);
	    rankToValue.put("King", 13);
	    rankToValue.put("Ace", 14);
	}
	
		private Card getHighCard(List<Card> hand, List<Card> communityCards) {
	    List<Card> allCards = new ArrayList<>(hand);
	    allCards.addAll(communityCards);

	    return allCards.stream()
	            .max((c1, c2) -> Integer.compare(rankToValue.get(c1.getRank()), rankToValue.get(c2.getRank())))
	            .orElse(null); // Return the highest card or null if the list is empty.
		}
		
		private int compareCards(Card card1, Card card2) {
		    if (card1 == null && card2 == null) return 0;
		    if (card1 == null) return -1;
		    if (card2 == null) return 1;

		    return Integer.compare(rankToValue.get(card1.getRank()), rankToValue.get(card2.getRank()));
		}



		private static int getCardValue(Card card) {
		    return rankToValue.get(card.getRank());
		}

		private static Map<String, Long> countRanks(List<Card> cards) {
		    return cards.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
		}

		private static Map<String, Long> countSuits(List<Card> cards) {
		    return cards.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
		}
		
		private static boolean checkStraight(List<Card> cards) {
		    List<Integer> uniqueValues = cards.stream()
		        .map(HandEvaluator::getCardValue)
		        .distinct()
		        .sorted(Comparator.reverseOrder())
		        .collect(Collectors.toList());

		    int consecutiveCount = 1;
		    for (int i = 0; i < uniqueValues.size() - 1; i++) {
		        if (uniqueValues.get(i) - uniqueValues.get(i + 1) == 1) {
		            consecutiveCount++;
		            if (consecutiveCount == 5) {
		                return true;
		            }
		        } else {
		            consecutiveCount = 1;
		        }
		    }

		    // Special Case: A, 2, 3, 4, 5
		    return uniqueValues.containsAll(List.of(14, 5, 4, 3, 2));
		}
		
		private static boolean isRoyalFlush(List<Card> cards) {
		    List<Card> flushCards = cards.stream()
		        .collect(Collectors.groupingBy(Card::getSuit))
		        .values().stream()
		        .filter(group -> group.size() >= 5)
		        .findFirst()
		        .orElse(List.of());

		    List<Integer> flushValues = flushCards.stream()
		        .map(HandEvaluator::getCardValue)
		        .collect(Collectors.toList());

		    return flushValues.containsAll(List.of(14, 13, 12, 11, 10));
		}
	
	public static int evaluateHand(List<Card> hand, List<Card> communityCards) {
        // Combine hand and community cards.
        List<Card> allCards = new ArrayList<>(hand);
        allCards.addAll(communityCards);

        // Evaluate the best poker hand and return a score.
        // Example: High card = 1, Pair = 2, Two pair = 3, etc.
        return scoreHand(allCards);
    }

	private static int scoreHand(List<Card> cards) {
	    Collections.sort(cards, Comparator.comparingInt(HandEvaluator::getCardValue).reversed());
	    Map<String, Long> rankCounts = countRanks(cards);
	    Map<String, Long> suitCounts = countSuits(cards);

	    boolean isFlush = suitCounts.values().stream().anyMatch(count -> count >= 5);
	    boolean isStraight = checkStraight(cards);

	    if (isFlush && isStraight) {
	        return isRoyalFlush(cards) ? 10 : 9; // Royal Flush or Straight Flush
	    }

	    if (rankCounts.containsValue(4L)) {
	        return 8; // Four of a Kind
	    }

	    if (rankCounts.containsValue(3L) && rankCounts.containsValue(2L)) {
	        return 7; // Full House
	    }

	    if (isFlush) {
	        return 6; // Flush
	    }

	    if (isStraight) {
	        return 5; // Straight
	    }

	    if (rankCounts.containsValue(3L)) {
	        return 4; // Three of a Kind
	    }

	    if (rankCounts.values().stream().filter(count -> count == 2).count() >= 2) {
	        return 3; // Two Pair
	    }

	    if (rankCounts.containsValue(2L)) {
	        return 2; // Pair
	    }

	    else {
	    	return 1; // High Card
	    }
	    
	    
	    
	}//end scorehand

}
