package ex04.user;


public interface UsersList {

    void addUser(User user);
    User get(int index);
    User getByID(int ID) throws UserNotFoundException;
    int size();
}
