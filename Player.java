// Represents a player in the game
package com.texasHoldem.game;

public class Player {
    private String name;
    private int balance;

    public Player(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void deductBalance(int amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance!");
        }
    }

    public void addWinnings(int amount) {
        balance += amount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
