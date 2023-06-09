package ex04.service;

import ex04.service.transaction.IllegalTransactionException;
import ex04.service.transaction.Transaction;
import ex04.service.transaction.TransactionLinkedList;
import ex04.service.transaction.TransactionList;
import ex04.user.User;
import ex04.user.UserNotFoundException;
import ex04.user.UsersArrayList;
import ex04.user.UsersList;

import java.util.UUID;

public class TransactionsService {

    UsersList usersList = new UsersArrayList();


    public void addUser(User user) {
        usersList.addUser(user);
    }

    public double getUserBalance(int id) {
        return usersList.getByID(id).getBalance();
    }

    public void transactionExecution(int recipientID, int senderID, double transactionAmount)
        throws UserNotFoundException {

        User recipient = usersList.getByID(recipientID);
        User sender = usersList.getByID(senderID);

        if (sender.getBalance() < transactionAmount) {
            throw new IllegalTransactionException("Сумма перевода превышает доступный баланс отправителя");
        } else if (recipientID == senderID) {
            throw new IllegalTransactionException("Ошибка перевода, получатель и отправитель совпадают");
        }

        UUID identifier = UUID.randomUUID();

        sender.getTransactionList().add(new Transaction(identifier, recipient, sender, Transaction.Category.CREDIT, -transactionAmount));
        recipient.getTransactionList().add(new Transaction(identifier, recipient, sender, Transaction.Category.DEBIT, transactionAmount));

        sender.setBalance(sender.getBalance() - transactionAmount);
        recipient.setBalance(recipient.getBalance() + transactionAmount);

    }

    public Transaction [] getUserTransaction (int userID) throws UserNotFoundException {
        return usersList.getByID(userID).getTransactionList().toArray();
    }

    public void removeTransaction (UUID transactionID, int userID) throws UserNotFoundException {
        usersList.getByID(userID).getTransactionList().remove(transactionID);
    }

    public Transaction [] getInvalidTransactionArray() {
        TransactionList invalidTransaction = new TransactionLinkedList();
        TransactionList allTransaction = new TransactionLinkedList();

        for (int i = 0; i < usersList.size(); i++) {
            Transaction [] userTransactionArray = usersList.get(i).getTransactionList().toArray();
            for (Transaction transaction : userTransactionArray) {
                allTransaction.add(transaction);
            }
        }

        boolean isInvalid;
        Transaction [] allTransactionArray = allTransaction.toArray();
        for (int i = 0; i < allTransactionArray.length; i++) {
            isInvalid = true;
            for (int j = 0; j < allTransactionArray.length; j++) {
                if (i == j) {
                    continue;
                }
                if (allTransactionArray[i].getIdentifier() == allTransactionArray[j].getIdentifier()) {
                    isInvalid = false;
                    break;
                }
            }
            if (isInvalid) {
                invalidTransaction.add(allTransactionArray[i]);
            }
        }

        return invalidTransaction.toArray();
    }
}
