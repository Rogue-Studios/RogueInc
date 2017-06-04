public class Resource {

    private String name;
    private boolean unlocked;
    private double producerCost;
    private double storerCost;
    private double marketValue;
    private int currentStorage;
    private int maxStorage;
    private double productionTime;
    private double timeSinceProduction;
    private int producerCount;
    private int storerCount;
    private double speedModifier;

    public Resource(String name, double producerCost, double storerCost, double marketValue, double productionTime) {

        this.name = name;
        this.unlocked = false;
        this.producerCost = producerCost;
        this.storerCost = storerCost;
        this.marketValue = marketValue;
        this.currentStorage = 0;
        this.maxStorage = 0;
        this.productionTime = productionTime;
        this.timeSinceProduction = 0;
        this.producerCount = 0;
        this.storerCount = 0;
        this.speedModifier = 1;

    }

    public String getName() {
        return name;
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

    public int getCurrentStorage() {
        return currentStorage;
    }

    public int getMaxStorage() {
        return maxStorage;
    }

    public double getProductionTime() {
        return productionTime;
    }

    public double getTimeSinceProduction() {
        return timeSinceProduction;
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

    public void setName(String name) {
        this.name = name;
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

    public void setCurrentStorage(int currentStorage) {
        this.currentStorage = currentStorage;
    }

    public void setMaxStorage(int maxStorage) {
        this.maxStorage = maxStorage;
    }

    public void setProductionTime(double productionTime) {
        this.productionTime = productionTime;
    }

    public void setTimeSinceProduction(double timeSinceProduction) {
        this.timeSinceProduction = timeSinceProduction;
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
}
