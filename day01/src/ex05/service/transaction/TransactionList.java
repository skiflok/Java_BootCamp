package ex05.service.transaction;

import java.util.UUID;

public interface TransactionList {

    void add(Transaction transaction);
    Transaction remove(UUID uuid);
    Transaction[] toArray();
}
