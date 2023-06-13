package ex05.command.commands;

import ex05.ConsoleHelper;
import ex05.command.Command;
import ex05.service.TransactionsService;
import ex05.service.transaction.Transaction;
import ex05.user.User;


public final class ViewAllTransactionsForSpecificUser implements Command {
    private final TransactionsService service;

    public ViewAllTransactionsForSpecificUser(TransactionsService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        ConsoleHelper.writeMessage("Enter a user ID");
        try {
            int userID = Integer.parseInt(ConsoleHelper.readString());
            Transaction[] transactions = service.getUserTransaction(userID);
            String toOrFrom;
            User anotherUser;
            for (Transaction transaction : transactions) {
                if (transaction.getSender().getIdentifier() == userID) {
                    toOrFrom = "To";
                    anotherUser = transaction.getRecipient();
                } else {
                    toOrFrom = "from";
                    anotherUser = transaction.getSender();
                }
                ConsoleHelper.writeMessage(String.format("%s %s(id = %d) %.2f with id = %s",
                        toOrFrom,
                        anotherUser.getName(),
                        anotherUser.getIdentifier(),
                        transaction.getTransferAmount(),
                        transaction.getIdentifier()
                ));
            }

        } catch (NumberFormatException e) {
            ConsoleHelper.writeMessage("Некоректный ввод");
        } catch (Exception e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }


    }

}
