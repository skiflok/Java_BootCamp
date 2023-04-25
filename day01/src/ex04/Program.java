package ex04;

import ex04.service.transaction.Transaction;
import ex04.user.User;
import ex04.service.TransactionsService;
import ex04.user.UserNotFoundException;

import java.util.Arrays;

public class Program {


    public static void main(String[] args)  {

        TransactionsService transactionsService = new TransactionsService();

        User user1 = new User("user_1", 1000_000);
        User user2 = new User("user_2", 500_000);

        transactionsService.addUser(user1);
        transactionsService.addUser(user2);

        try {
            transactionsService.transactionExecution(user1.getIdentifier(), user2.getIdentifier(), 1000);
            transactionsService.transactionExecution(user1.getIdentifier(), user2.getIdentifier(), 500);
            transactionsService.transactionExecution(user2.getIdentifier(), user1.getIdentifier(), 10_000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            transactionsService.transactionExecution(user1.getIdentifier(), user2.getIdentifier(), -500);
        } catch (Exception e) {
            System.out.println("Ощибка выполнения транзакции");
        }

        try {
            transactionsService.transactionExecution(user2.getIdentifier(), user2.getIdentifier(), 10_000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            transactionsService.transactionExecution(user2.getIdentifier(), user2.getIdentifier(), 10_000_000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Transaction [] transactions;

        try {
            transactions = transactionsService.getUserTransaction(user1.getIdentifier());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Arrays.toString(transactions));

        try {
            transactionsService.removeTransaction(transactions[0].getIdentifier(), user1.getIdentifier());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        Transaction [] invalidTransactionArray = transactionsService.getInvalidTransactionArray();

        System.out.println(Arrays.toString(invalidTransactionArray));


    }
}
