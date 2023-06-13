package ex03;

import java.util.Arrays;
import java.util.UUID;

public class Program {


    public static void main(String[] args) {

        UsersList usersList = new UsersArrayList();
        TransactionList transactionList = new TransactionLinkedList();

        for (int i = 0; i < 15; i++) {
            usersList.addUser(new User("user" + i, 1000d));
        }

        for (int i = 0; i < usersList.size(); i++) {
            System.out.println(usersList.get(i));
        }

        Transaction t1 = null;
        Transaction t2 = null;

        try {
            t1 = new Transaction(usersList.get(0), usersList.get(1), Transaction.Category.DEBIT, 100);
            t2  = new Transaction(usersList.get(0), usersList.get(1), Transaction.Category.CREDIT, -150);
        } catch (RuntimeException e) {
            System.out.println("Transaction error");
        }

        transactionList.add(t1);
        transactionList.add(t2);
        System.out.println(t1);
        System.out.println(t2);

        System.out.println("Список транзакций " + Arrays.toString(transactionList.toArray()));

        UUID temp = t1.getIdentifier();

        try {
            transactionList.remove(transactionList.toArray()[0].getIdentifier());
            transactionList.remove(temp);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            transactionList.remove(null);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Список транзакций " + Arrays.toString(transactionList.toArray()));

        try {
            transactionList.remove(transactionList.toArray()[0].getIdentifier());
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Список транзакций " + Arrays.toString(transactionList.toArray()));

        try {
            transactionList.remove(temp);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(usersList.getByID(0));
            System.out.println(usersList.getByID(20));
        } catch (UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("usersList.size() = " + usersList.size());


    }
}
