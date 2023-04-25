package ex04.user;

import java.util.Arrays;

public class UsersArrayList implements UsersList {

    private int capacity = 10;
    private User[] array = new User[capacity];
    private int size = 0;


    @Override
    public void addUser(User user) {
        if (size + 1 > capacity) {
            resize();
        }
        array[size] = user;
        size++;
    }

    @Override
    public User get(int index) {
        return array[index];
    }

    @Override
    public User getByID(int ID) throws UserNotFoundException {
        for (int i = 0; i < size; i++) {
            if (array[i].getIdentifier() == ID) {
                return array[i];
            }
        }
        throw new UserNotFoundException("Пользователь с ID = " + ID + " не найден");
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        capacity *= 2;
        User[] newArray = new User[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
