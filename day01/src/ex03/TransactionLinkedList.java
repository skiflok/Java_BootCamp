package ex03;

import java.util.LinkedList;
import java.util.UUID;

public class TransactionLinkedList implements TransactionList {

    private int size = 0;
    private Node first;
    private Node last;


    @Override
    public void add(Transaction transaction) {
        final Node l = last;
        final Node newNode = new Node(l, transaction, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void remove(UUID uuid) {
        if (size == 0) {
            throw new TransactionNotFoundException("Транзакция не найдена");
        }

        if (uuid == null) {
            throw new TransactionNotFoundException("Передан null в качестве ID");
        }

        for (Node x = first; x != null; x = x.next) {
            if (uuid.equals(x.transaction.getIdentifier())) {
                unlink(x);
                return;
            }
        }
        throw new TransactionNotFoundException("Пользователь не найден");
    }

    private void unlink(Node x) {

        final Node next = x.next;
        final Node prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.transaction = null;
        size--;
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node temp = first;
        for (int i = 0; i < size; i++) {
            transactions[i] = temp.transaction;
            temp = temp.next;
        }
        return transactions;
    }

    private class Node {
        Node prev;
        Node next;
        Transaction transaction;

        public Node(Node prev, Transaction transaction, Node next) {
            this.prev = prev;
            this.next = next;
            this.transaction = transaction;
        }
    }

}
