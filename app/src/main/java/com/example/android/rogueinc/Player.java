package com.example.android.rogueinc;

public class Player {

    double balance;
    int goldBalance;
    double totalMoneyCollected;
    double totalMoneySpent;

    public Player(Double balance, int goldBalance) {

        this.balance = balance;
        this.goldBalance = goldBalance;
        this.totalMoneyCollected = balance;
        this.totalMoneySpent = 0;

    }

    public void addBalance(double amount) {
        this.balance += amount;
        this.totalMoneyCollected += amount;
    }

    public void spendBalance(double amount) {
        this.balance -= amount;
        this.totalMoneySpent += balance;
    }

}
