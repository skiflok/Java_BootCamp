package ex0;

import java.util.UUID;

public class Transaction {

    private UUID identifier;
    private User recipient;
    private User sender;
    private Category TransferCategory;
    private double TransferAmount;

    public enum Category {
        DEBIT,
        CREDIT
    }

    public Transaction(User recipient, User sender, Category transferCategory, double transferAmount) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        TransferCategory = transferCategory;
        TransferAmount = transferAmount;
    }
}
