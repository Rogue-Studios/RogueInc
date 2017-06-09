import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class User {

    private DoubleProperty balance;
    private double lifetimeMoney;


    private double lifetimeSpending;
    private int totalResourcesProduced;


    private IntegerProperty buyProductionIncrement;
    private IntegerProperty buyStorageIncrement;
    private IntegerProperty sellResourceIncrement;



    public User(double balance) {
        this.balance = new SimpleDoubleProperty();
        this.balance.setValue(balance);
        lifetimeMoney = balance;
        lifetimeSpending = 0;
        totalResourcesProduced = 0;

        this.buyProductionIncrement = new SimpleIntegerProperty();
        this.buyProductionIncrement.setValue(1);
        this.buyStorageIncrement = new SimpleIntegerProperty();
        this.buyStorageIncrement.setValue(1);
        this.sellResourceIncrement = new SimpleIntegerProperty();
        this.sellResourceIncrement.setValue(1);
    }

    public double calculateMaxToFillStorage(Resource resource) {
        double cost = 0;

        // Find the max number that could be bought with current balance

        int numPossibleToBuyWithBalance;

        int balance = balanceProperty().getValue().intValue();
        int itemCost = new Double(resource.getProducerCost()).intValue();

        numPossibleToBuyWithBalance = balance / itemCost;

        // Find the max number that could be bought with current storage

        int numPossibleToBuyWithStorage = resource.getMaxStorage() - resource.getCurrentStorage();

        int numPossibleOverall;

        // Use whichever value is smallest when calculating cost

        if (numPossibleToBuyWithStorage < numPossibleToBuyWithBalance) {
            numPossibleOverall = numPossibleToBuyWithStorage;
        } else {
            numPossibleOverall = numPossibleToBuyWithBalance;
        }

        cost = resource.getProducerCost() * numPossibleOverall;

        return cost;
    }

    public int calculateMaxAmount(Double itemCost) {
        int max = (int) Math.floor(balanceProperty().getValue() / itemCost);
        return max;
    }

    public int calculateMaxSellAmount(Resource resource) {
        int max = resource.currentStorageProperty().get();
        return max;
    }

    public double calculateMaxCost(Resource resource, Double itemCost) {
        // Find the max number that could be bought with current balance

        int balance = balanceProperty().getValue().intValue();
        int itemCostInt = new Double(itemCost).intValue();

        int numPossibleToBuyWithBalance = balance / itemCostInt;

        double cost = itemCost * numPossibleToBuyWithBalance;

        return cost;

    }

    public double calculateMaxSell(Resource resource, Double itemValue) {
        // Find max value gained from selling all in storage

        return resource.getCurrentStorage() * itemValue;
    }

    public void buyProducer(Resource resource) {
        // gives more production to the user if they can afford it
        if(buyProductionIncrementProperty().getValue() == -1) {
            if (isAbleToSpend(resource.getProducerCost() * resource.getMaxProductionBuy())) {
                resource.producerCountProperty().set(resource.producerCountProperty().get() + resource.getMaxProductionBuy());
                subtractMoney(resource.getProducerCost() * resource.getMaxProductionBuy());
            }
        }
        else {
            if (isAbleToSpend(resource.getProducerCost() * buyProductionIncrementProperty().getValue())) {
                resource.producerCountProperty().set(resource.producerCountProperty().get() + buyProductionIncrementProperty().get());
                subtractMoney(resource.getProducerCost() * buyProductionIncrementProperty().get());
            }
        }
    }

    public void buyStorer(Resource resource) {
        // gives the user more storage for the resource if user can afford it
        if(buyStorageIncrementProperty().getValue() == -1) {
            if (isAbleToSpend(resource.getStorerCost() * resource.getMaxStorageBuy())) {
                resource.maxStorageProperty().set(resource.maxStorageProperty().get() + (resource.getStorageIncrement() * resource.getMaxStorageBuy()));
                resource.setStorerCount(resource.getStorerCount() + resource.getMaxStorageBuy());
                subtractMoney(resource.getProducerCost() * resource.getMaxStorageBuy());
            }
        }
        else {
            if (isAbleToSpend(resource.getProducerCost() * buyProductionIncrementProperty().getValue())) {
                resource.maxStorageProperty().set(resource.maxStorageProperty().get() + (resource.getStorageIncrement() * buyStorageIncrementProperty().get()));
                resource.setStorerCount(resource.getStorerCount() + buyStorageIncrementProperty().get());
                subtractMoney(resource.getStorerCost() * buyStorageIncrementProperty().get());
            }
        }
    }

    public void sell(Resource resource) {
        // sell single item if user has enough items
        if(sellResourceIncrementProperty().getValue() == -1) {
            if (resource.currentStorageProperty().getValue() >= resource.getMaxSell()) {
                resource.currentStorageProperty().set(resource.currentStorageProperty().get() - resource.getMaxSell());
                addMoney(resource.getMarketValue() * resource.getMaxSell());
            }
        }
        else {
            if (resource.currentStorageProperty().get() >= sellResourceIncrementProperty().get()) {
                resource.currentStorageProperty().set(resource.currentStorageProperty().get() - sellResourceIncrementProperty().get());
                addMoney(resource.getMarketValue() * sellResourceIncrementProperty().get());
            }
        }

    }

    public boolean isAbleToSpend(double money) {
        return money <= balance.get();
    }

    public void addMoney(double money) {
        balance.set(balance.get() + money);
        lifetimeMoney += money;
    }

    public void subtractMoney(double money) {
        balance.set(balance.get() - money);
        lifetimeSpending += money;
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }



    public void setBuyProductionIncrement(int buyProductionIncrement) {
        this.buyProductionIncrement.set(buyProductionIncrement);
    }

    public void setBuyStorageIncrement(int buyStorageIncrement) {
        this.buyStorageIncrement.set(buyStorageIncrement);
    }

    public void setSellResourceIncrement(int sellResourceIncrement) {
        this.sellResourceIncrement.set(sellResourceIncrement);
    }

    public int getBuyProductionIncrement() {
        return buyProductionIncrement.get();
    }

    public IntegerProperty buyProductionIncrementProperty() {
        return buyProductionIncrement;
    }

    public int getBuyStorageIncrement() {
        return buyStorageIncrement.get();
    }

    public IntegerProperty buyStorageIncrementProperty() {
        return buyStorageIncrement;
    }

    public int getSellResourceIncrement() {
        return sellResourceIncrement.get();
    }

    public IntegerProperty sellResourceIncrementProperty() {
        return sellResourceIncrement;
    }

}