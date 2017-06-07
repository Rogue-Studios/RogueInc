import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class User {

    private DoubleProperty balance;
    private double lifetimeMoney;
    private double lifetimeSpending;
    private int totalResourcesProduced;

    public User(double balance) {
        this.balance = new SimpleDoubleProperty();
        this.balance.setValue(100);
        lifetimeMoney = balance;
        lifetimeSpending = 0;
        totalResourcesProduced = 0;
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

}

