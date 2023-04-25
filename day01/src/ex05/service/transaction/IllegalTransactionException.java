package ex05.service.transaction;

public class IllegalTransactionException extends RuntimeException{

    public IllegalTransactionException() {}

    public IllegalTransactionException(String msg) {
        super(msg);
    }

}
