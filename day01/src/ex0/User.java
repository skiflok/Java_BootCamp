package ex0;

public class User {
    private final int identifier;
    private String name;
    private double balance;

    public User(int identifier, String name, double balance) {
        this.identifier = identifier;
        this.name = name;
        this.balance = balance;
        if (balance < 0) {
            System.err.println("Баланс не может быть отрицательным");
            System.exit(-1);
        }
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}
