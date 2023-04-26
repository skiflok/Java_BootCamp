package ex05.command.commands;

import ex05.ConsoleHelper;
import ex05.command.Command;
import ex05.service.TransactionsService;
import ex05.user.UserNotFoundException;

public class ViewUserBalance implements Command {

    private final TransactionsService service;

    public ViewUserBalance(TransactionsService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        ConsoleHelper.writeMessage("Enter a user ID");
        String input = ConsoleHelper.readString();
        int userId;
        try {
            userId = Integer.parseInt(input);
            ConsoleHelper.writeMessage(String.format("%s - %.2f",
                    service.getUsersList().getByID(userId).getName(),
                    service.getUserBalance(userId)));
        } catch (NumberFormatException e) {
            ConsoleHelper.writeMessage("Некорректый ввод ID");
        } catch (UserNotFoundException e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }
    }
}
