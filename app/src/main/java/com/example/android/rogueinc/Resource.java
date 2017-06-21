package com.example.android.rogueinc;

public class Resource {

    private String name;
    private boolean unlocked;
    private double value;
    private double cost;
    private double productionTime;
    private double timeSinceProduction;
    private int amount;
    private int amountUsed;
    private int amountProduced;
    private double speedModifier;
    private double valueModifier;
    private boolean started;

    public Resource(String name, Boolean unlocked, double value, double cost, double productionTime) {

        this.name = name;
        this.unlocked = unlocked;
        this.value = value;
        this.cost = cost;
        this.productionTime = productionTime;
        this.timeSinceProduction = 0;
        this.amount = 0;
        this.speedModifier = 1.0;
        this.valueModifier = 1.0;
        this.started = false;
    }

    public double update(double time) {
        timeSinceProduction += ((time / 1000) * speedModifier);
        if ((timeSinceProduction / productionTime) > 1) {
            started = false;
            int timesProduced = (int) Math.floor(timeSinceProduction / productionTime);
            timeSinceProduction = timeSinceProduction % productionTime;
            amountProduced += timesProduced;
            return (timesProduced * value * valueModifier);
        }
        else {
            return 0;
        }
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

    public int getAmountUsed() {
        return amountUsed;
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

    public void setAmountUsed(int amountUsed) {
        this.amountUsed = amountUsed;
    }

    public void setSpeedModifier(double speedModifier) {
        this.speedModifier = speedModifier;
    }

    public void setValueModifier(double valueModifier) {
        this.valueModifier = valueModifier;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
