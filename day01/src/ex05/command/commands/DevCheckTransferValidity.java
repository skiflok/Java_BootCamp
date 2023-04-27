package ex05.command.commands;

import ex05.ConsoleHelper;
import ex05.command.Command;
import ex05.service.TransactionsService;
import ex05.service.transaction.Transaction;
import ex05.service.transaction.TransactionList;
import ex05.user.User;
import ex05.user.UsersArrayList;
import ex05.user.UsersList;

import javax.jws.soap.SOAPBinding;
import java.util.UUID;

public class DevCheckTransferValidity implements Command {

    private final TransactionsService service;

    public DevCheckTransferValidity(TransactionsService service) {
        this.service = service;
    }

    @Override
    public void execute() {

        try {
            Transaction[] transactions = service.getInvalidTransactionArray();
            if (transactions.length != 0) {
                ConsoleHelper.writeMessage("Check results:");
                User user;
                for (Transaction t : transactions) {
                    if (t.getTransferCategory().equals(Transaction.Category.CREDIT)) {
                        user = t.getSender();
                    } else {
                        user = t.getRecipient();
                    }
                    ConsoleHelper.writeMessage(String.format("%s(id = %d) has an unacknowledged transfer id = %s",
                            user.getName(),
                            user.getIdentifier(),
                            t.getIdentifier()
                    ));
                }
            } else {
                ConsoleHelper.writeMessage("Не валидные транзакции отсутствуют");
            }


        } catch (NumberFormatException e) {
            ConsoleHelper.writeMessage("Некорректный ввод");
        } catch (Exception e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }
    }
}