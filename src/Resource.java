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
    private int producerCount;
    private int storerCount;
    private double speedModifier;

    public Resource(String name, double producerCost, double storerCost, double marketValue, double productionTime) {

        this.name = new SimpleStringProperty();
        this.name.setValue(name);

        this.unlocked = false;
        this.producerCost = producerCost;
        this.storerCost = storerCost;
        this.marketValue = marketValue;

        this.currentStorage = new SimpleIntegerProperty();
        this.currentStorage.setValue(0);
        this.maxStorage = new SimpleIntegerProperty();
        this.maxStorage.setValue(0);

        this.productionTime = productionTime;
        this.timeSinceProduction = new SimpleDoubleProperty();
        this.timeSinceProduction.setValue(0);

        this.producerCount = 0;
        this.storerCount = 0;
        this.speedModifier = 1;

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


    public int getProducerCount() {
        return producerCount;
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

    public void setProducerCount(int producerCount) {
        this.producerCount = producerCount;
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
}
