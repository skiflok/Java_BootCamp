package ex05.user;

import ex05.service.transaction.TransactionLinkedList;
import ex05.service.transaction.TransactionList;

public class User {
    private final int identifier;
    private String name;
    private double balance;
    private final TransactionList transactionList = new TransactionLinkedList();

    public User( String name, double balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
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

    public TransactionList getTransactionList() {
        return transactionList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
         return String.format("ID = %d name = %s balance = %.2f",
                 identifier,
                 name,
                 balance
         );
    }
}
