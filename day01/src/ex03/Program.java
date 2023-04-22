package ex03;

public class Program {


    public static void main(String[] args) {

        UsersList usersList = new UsersArrayList();
        for (int i = 0; i < 15; i++) {
            usersList.addUser(new User("user" + i, 1000d));
        }

        System.out.println(usersList.get(0));
        System.out.println(usersList.get(1));

        try {
            Transaction t1 = new Transaction(usersList.get(0), usersList.get(1), Transaction.Category.DEBIT, 100);
            Transaction t2 = new Transaction(usersList.get(0), usersList.get(1), Transaction.Category.CREDIT, -150);
            System.out.println(t1);
            System.out.println(t2);
        } catch (Exception e) {
            System.out.println("Transaction error");
        }


        try {
            System.out.println(usersList.getByID(0));
            System.out.println(usersList.getByID(20));
        } catch (UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println(usersList.size());

    }

}
