package com.example.android.rogueinc;

public class Resource {

    private String name;
    private boolean unlocked;
    private double value;
    private double cost;
    private double productionTime;
    private double timeSinceProduction;
    private int amount;
    private double speedModifier;
    private double valueModifier;

    public Resource(String name, double value, double cost, double productionTime) {

        this.name = name;
        this.unlocked = false;
        this.value = value;
        this.cost = cost;
        this.productionTime = productionTime;
        this.timeSinceProduction = 0;
        this.amount = 0;
        this.speedModifier = 1.0;
        this.valueModifier = 1.0;

    }

    public String getName() {
        return name;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public double getValue() {
        return value;
    }

    public double getCost() {
        return cost;
    }

    public double getProductionTime() {
        return productionTime;
    }

    public double getTimeSinceProduction() {
        return timeSinceProduction;
    }

    public int getAmount() {
        return amount;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public double getValueModifier() {
        return valueModifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setProductionTime(double productionTime) {
        this.productionTime = productionTime;
    }

    public void setTimeSinceProduction(double timeSinceProduction) {
        this.timeSinceProduction = timeSinceProduction;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSpeedModifier(double speedModifier) {
        this.speedModifier = speedModifier;
    }

    public void setValueModifier(double valueModifier) {
        this.valueModifier = valueModifier;
    }

}
