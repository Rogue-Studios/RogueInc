import javafx.beans.property.*;

public class Resource {

    private StringProperty name;
    private boolean unlocked;
    private double producerCost;
    private double storerCost;
    private double marketValue;

    private IntegerProperty currentStorage;
    private IntegerProperty maxStorage;

    private double productionTime;
    private DoubleProperty timeSinceProduction;

    private IntegerProperty producerCount;
    private int storerCount;
    private double speedModifier;

    private int storageIncrement;
    private int maxProductionBuy;
    private int maxStorageBuy;
    private int maxSell;

    public int getStorageIncrement() {
        return storageIncrement;
    }


    public Resource(String name, double producerCost, double storerCost, double marketValue, double productionTime, int storageIncrement) {

        this.name = new SimpleStringProperty();
        this.name.setValue(name + String.format(" ($%.0f)", marketValue));

        this.unlocked = false;
        this.producerCost = producerCost;
        this.storerCost = storerCost;
        this.marketValue = marketValue;

        this.currentStorage = new SimpleIntegerProperty();
        this.currentStorage.setValue(0);
        this.maxStorage = new SimpleIntegerProperty();
        this.maxStorage.setValue(10);

        this.productionTime = productionTime;
        this.timeSinceProduction = new SimpleDoubleProperty();
        this.timeSinceProduction.setValue(0);
        this.producerCount = new SimpleIntegerProperty();
        this.producerCount.setValue(1);

        this.storageIncrement = storageIncrement;

        this.storerCount = 0;
        this.speedModifier = 1;

    }

    public double updateResourceData(double timePassed, double overFlowModifier) {

        // this is overkill for normal running but is necessary for relaunching the program and calculating the difference in time
        double overFlowMoney = 0;
        timeSinceProduction.set(timeSinceProduction.get() + timePassed);

        if (timeSinceProduction.get() > productionTime) {
            int numberOfProductions;

            // calculates how many times a resource has been produced
            numberOfProductions = (int) (Math.floor(timeSinceProduction.get() / productionTime)) * producerCountProperty().get();

            // checks to see if storage will overflow
            if (currentStorage.get() + numberOfProductions > maxStorage.get()) {

                // calculates money from storage overflow
                overFlowMoney = marketValue * overFlowModifier * ((currentStorage.get() + numberOfProductions) - maxStorage.get());

                // sets currentStorage to full
                currentStorage.set(maxStorage.get());
            } else {
                // adds all new resources to storage if it will not overflow
                currentStorage.set(currentStorage.get() + numberOfProductions);
            }
            // sets timeSinceProduction to correct amount
            timeSinceProduction.set(timeSinceProduction.get() % productionTime);

        }
        return overFlowMoney;
    }

    public double getTotalValue() {

        // get the value of all items in storage
        double totalValue;
        totalValue = marketValue * currentStorage.get();
        return totalValue;

    }



    public boolean isUnlocked() {
        return unlocked;
    }

    public double getProducerCost() {
        return producerCost;
    }

    public double getStorerCost() {
        return storerCost;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public int getStorerCount() {
        return storerCount;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void setProducerCost(double producerCost) {
        this.producerCost = producerCost;
    }

    public void setStorerCost(double storerCost) {
        this.storerCost = storerCost;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }


    public void setProductionTime(double productionTime) {
        this.productionTime = productionTime;
    }

    public void setStorerCount(int storerCount) {
        this.storerCount = storerCount;
    }

    public void setSpeedModifier(double speedModifier) {
        this.speedModifier = speedModifier;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getCurrentStorage() {
        return currentStorage.get();
    }

    public IntegerProperty currentStorageProperty() {
        return currentStorage;
    }

    public void setCurrentStorage(int currentStorage) {
        this.currentStorage.set(currentStorage);
    }

    public int getMaxStorage() {
        return maxStorage.get();
    }

    public IntegerProperty maxStorageProperty() {
        return maxStorage;
    }

    public void setMaxStorage(int maxStorage) {
        this.maxStorage.set(maxStorage);
    }

    public double getProductionTime() {
        return productionTime;
    }

    public double getTimeSinceProduction() {
        return timeSinceProduction.get();
    }

    public DoubleProperty timeSinceProductionProperty() {
        return timeSinceProduction;
    }

    public void setTimeSinceProduction(double timeSinceProduction) {
        this.timeSinceProduction.set(timeSinceProduction);
    }

    public IntegerProperty producerCountProperty() {
        return producerCount;
    }

    public void setProducerCountProperty(double producerCount) {
        this.setProducerCountProperty(producerCount);
    }

    public int getMaxProductionBuy() {
        return maxProductionBuy;
    }

    public int getMaxStorageBuy() {
        return maxStorageBuy;
    }

    public int getMaxSell() {
        return maxSell;
    }

    public void setMaxProductionBuy(int maxProductionBuy) {
        this.maxProductionBuy = maxProductionBuy;
    }

    public void setMaxStorageBuy(int maxStorageBuy) {
        this.maxStorageBuy = maxStorageBuy;
    }

    public void setMaxSell(int maxSell) {
        this.maxSell = maxSell;
    }
}
