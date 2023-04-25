package ex04.service;

import ex04.service.transaction.IllegalTransactionException;
import ex04.service.transaction.Transaction;
import ex04.user.User;
import ex04.user.UsersArrayList;
import ex04.user.UsersList;

import java.util.UUID;

public class TransactionsService {

    UsersList usersList = new UsersArrayList();


    public void addUser(User user) {
        usersList.addUser(user);
    }

    public double getUserBalance(User user) {
        return user.getBalance();
    }

    public void transactionExecution(int recipientID, int senderID, double transactionAmount) throws Exception {

        if (usersList.get(senderID).getBalance() < transactionAmount) {
            throw new IllegalTransactionException("Сумма перевода превышает доступный баланс отправителя");
        } else if (recipientID == senderID) {
            throw new IllegalTransactionException("Ошибка перевода, получатель и отправитель совпадают");
        }

        UUID identifier = UUID.randomUUID();

        Transaction temp = new Transaction(usersList.getByID(recipientID), usersList.getByID(senderID), Transaction.Category.DEBIT, transactionAmount);
        usersList.get(recipientID).getTransactionList().add(temp);
        usersList.get(senderID).getTransactionList().add(
                new Transaction(usersList.getByID(recipientID), usersList.getByID(senderID), Transaction.Category.DEBIT, -transactionAmount));

    }

}
