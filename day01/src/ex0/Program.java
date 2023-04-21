package ex0;

public class Program {


    public static void main(String[] args) {

        User user1 = new User(0, "user1", 1000d);
        User user2 = new User(1, "user2", 1000d);
        System.out.println(user1);
        System.out.println(user2);

        try {
            Transaction t1 = new Transaction(user1,user2, Transaction.Category.DEBIT, 100);
            Transaction t2 = new Transaction(user1,user2, Transaction.Category.CREDIT, -150);
            System.out.println(t1);
            System.out.println(t2);
        } catch (Exception e) {
            System.out.println("Transaction error");
        }

        System.out.println(user1);
        System.out.println(user2);
    }

}
