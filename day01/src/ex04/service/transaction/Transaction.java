package ex04.service.transaction;

import ex04.user.User;

import java.util.UUID;

public class Transaction {

    private final UUID identifier;
    private final User recipient;
    private final User sender;
    private final Category transferCategory;
    private final double transferAmount;

    public enum Category {
        DEBIT,
        CREDIT
    }

    public Transaction(User recipient, User sender, Category transferCategory, double transferAmount) throws Exception {
        switch (transferCategory) {
            case DEBIT:
                if (transferAmount < 0 || sender.getBalance() < transferAmount) {
                    throw new Exception();
            }
                break;
            case CREDIT:
                if (transferAmount > 0 || sender.getBalance() < -transferAmount) {
                    throw new Exception();
                }
                break;
        }
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
    }

    public Transaction(UUID identifier, User recipient, User sender, Category transferCategory, double transferAmount) {
        this.identifier = identifier;
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getTransferCategory() {
        return transferCategory;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    @Override
    public String toString() {
        return String.format("ID = %s sender - {%s}  recipient - {%s} transferCategory - %s transferAmount = %s",
                identifier,
                sender,
                recipient,
                transferCategory,
                transferAmount
        );
    }
}
