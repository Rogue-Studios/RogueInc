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

    public void buyProducer(Resource resource) {
		// gives more production to the user if they can afford it
		if (isAbleToSpend(resource.getProducerCost() * buyProductionIncrementProperty().get())) {
			resource.producerCountProperty().set(resource.producerCountProperty().get() + buyProductionIncrementProperty().get());
			subtractMoney(resource.getProducerCost() * buyProductionIncrementProperty().get());
		}
	}

    public void buyStorer(Resource resource) {
        // gives the user more storage for the resource if user can afford it
        if (isAbleToSpend(resource.getStorerCost() * buyStorageIncrementProperty().get())) {
            resource.maxStorageProperty().set(resource.maxStorageProperty().get() + (resource.getStorageIncrement() * buyStorageIncrementProperty().get()));
            resource.setStorerCount(resource.getStorerCount() + buyStorageIncrementProperty().get());
            subtractMoney(resource.getStorerCost() * buyStorageIncrementProperty().get());
        }
    }

    public void sell(Resource resource) {
		// sell single item if user has enough items
		if (resource.currentStorageProperty().get() >= sellResourceIncrementProperty().get()) {
			resource.currentStorageProperty().set(resource.currentStorageProperty().get() - sellResourceIncrementProperty().get());
			addMoney(resource.getMarketValue() * sellResourceIncrementProperty().get());
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

    public DoubleProperty getBalance() {
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

