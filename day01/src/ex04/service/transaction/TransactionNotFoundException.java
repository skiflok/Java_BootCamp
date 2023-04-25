package ex04.service.transaction;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException (String msg) {
        super(msg);
    }
}
