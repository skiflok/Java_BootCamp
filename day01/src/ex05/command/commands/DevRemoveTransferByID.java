package ex05.command.commands;

import ex05.ConsoleHelper;
import ex05.command.Command;
import ex05.service.TransactionsService;
import ex05.service.transaction.Transaction;
import ex05.user.User;

import java.util.UUID;

public class DevRemoveTransferByID implements Command {

    private final TransactionsService service;

    public DevRemoveTransferByID(TransactionsService service) {
        this.service = service;
    }

    @Override
    public void execute() {

        ConsoleHelper.writeMessage("Enter a user ID and a transfer ID");
        String [] input = ConsoleHelper.readString().trim().split(" ");
        if (input.length != 2)
            throw new IllegalArgumentException("Неверное количество аргументов");
        int userID;
        UUID uuid;

        try {
            userID = Integer.parseInt(input[0]);
            uuid = UUID.fromString(input[1]);

            Transaction transaction = service.removeTransaction(uuid, userID);

            String toOrFrom;
            User anotherUser;

                if (transaction.getSender().getIdentifier() == userID) {
                    toOrFrom = "To";
                    anotherUser = transaction.getRecipient();
                } else {
                    toOrFrom = "from";
                    anotherUser = transaction.getSender();
                }
                ConsoleHelper.writeMessage(String.format("Transfer %s %s(id = %d) %.2f removed",
                        toOrFrom,
                        anotherUser.getName(),
                        anotherUser.getIdentifier(),
                        transaction.getTransferAmount()
                ));

        } catch (NumberFormatException e) {
            ConsoleHelper.writeMessage("Некорректный ввод");
        } catch (Exception e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }

    }
}
