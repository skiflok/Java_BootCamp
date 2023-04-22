package ex02;

public class UserNotFoundException extends Exception{

    public UserNotFoundException () {
        super();
    }

    public UserNotFoundException (String message) {
        super(message);
    }
}
