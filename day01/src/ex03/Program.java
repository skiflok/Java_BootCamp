package ex03;

import java.util.Arrays;

public class Program {


    public static void main(String[] args) {

        UsersList usersList = new UsersArrayList();
        TransactionList transactionList = new TransactionLinkedList();

        for (int i = 0; i < 15; i++) {
            usersList.addUser(new User("user" + i, 1000d));
        }

        System.out.println(usersList.get(0));
        System.out.println(usersList.get(1));

        Transaction t1 = null;
        Transaction t2 = null;

        try {
            t1 = new Transaction(usersList.get(0), usersList.get(1), Transaction.Category.DEBIT, 100);
            t2  = new Transaction(usersList.get(0), usersList.get(1), Transaction.Category.CREDIT, -150);
        } catch (Exception e) {
            System.out.println("Transaction error");
        }

        transactionList.add(t1);
        transactionList.add(t2);
        System.out.println(t1);
        System.out.println(t2);


        System.out.println(Arrays.toString(transactionList.toArray()));

        try {
            transactionList.remove(transactionList.toArray()[0].getIdentifier());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(Arrays.toString(transactionList.toArray()));

        try {
            System.out.println(usersList.getByID(0));
            System.out.println(usersList.getByID(20));
        } catch (UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println(usersList.size());


    }
}
