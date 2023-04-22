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
            throw new TransactionNotFoundException("Пользователь не найден");
        }
        Node temp = first;
        while (temp != null) {

            if (temp.transaction.getIdentifier().equals(uuid)) {

            }

            temp = temp.next;
        }
        throw new TransactionNotFoundException("Пользователь не найден");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node temp = first;
        for (int i = 0; i < size; i++) {
            transactions[0] = temp.transaction;
            temp = temp.next;
        }

        return transactions;
    }

    private class Node {
        Node pref;
        Node next;
        Transaction transaction;

        public Node(Node pref, Transaction transaction, Node next) {
            this.pref = pref;
            this.next = next;
            this.transaction = transaction;
        }
    }

}
