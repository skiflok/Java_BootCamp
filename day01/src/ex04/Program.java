package ex04;


import ex04.service.transaction.Transaction;
import ex04.user.User;
import ex04.service.TransactionsService;
import ex04.user.UserNotFoundException;

public class Program {


    public static void main(String[] args) throws UserNotFoundException {

        TransactionsService transactionsService = new TransactionsService();

        User user1 = new User("user_1", 1000_000);
        User user2 = new User("user_2", 500_000);

        transactionsService.addUser(user1);
        transactionsService.addUser(user2);

        System.out.println("Баланс пользователя user1 = " + transactionsService.getUserBalance(user1.getIdentifier()));

        try {
            transactionsService.transactionExecution(user1.getIdentifier(), user2.getIdentifier(), 1000);
            transactionsService.transactionExecution(user1.getIdentifier(), user2.getIdentifier(), 500);
            transactionsService.transactionExecution(user2.getIdentifier(), user1.getIdentifier(), 10_000);
            System.out.println("Транзакуии прошли успешно");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        try {
            transactionsService.transactionExecution(user1.getIdentifier(), user2.getIdentifier(), -500);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        try {
            transactionsService.transactionExecution(user2.getIdentifier(), user2.getIdentifier(), 10_000);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        try {
            transactionsService.transactionExecution(user2.getIdentifier(), user2.getIdentifier(), 10_000_000);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        Transaction [] transactions = transactionsService.getUserTransaction(user1.getIdentifier());;

        System.out.println("Транзакции пользователя user1");
        for (Transaction t : transactions) {
            System.out.println(t);
        }

        try {
            transactionsService.removeTransaction(transactions[0].getIdentifier(), user1.getIdentifier());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        Transaction [] invalidTransactionArray = transactionsService.getInvalidTransactionArray();

        System.out.println("непарные транзакции user1");
        for (Transaction t : invalidTransactionArray) {
            System.out.println(t);
        }

    }
}
