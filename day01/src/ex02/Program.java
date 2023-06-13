package ex02;

public class Program {


    public static void main(String[] args) {

        UsersList usersList = new UsersArrayList();
        for (int i = 0; i < 15; i++) {
            usersList.addUser(new User("user" + i, 1000d));
        }

        for (int i = 0; i < usersList.size(); i++) {
            System.out.println(usersList.get(i));
        }

        try {
            Transaction t1 = new Transaction(usersList.get(0), usersList.get(1), Transaction.Category.DEBIT, 100);
            Transaction t2 = new Transaction(usersList.get(0), usersList.get(1), Transaction.Category.CREDIT, -150);
            System.out.println(t1);
            System.out.println(t2);
        } catch (RuntimeException e) {
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
