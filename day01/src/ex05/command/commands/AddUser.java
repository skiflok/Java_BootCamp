package ex05.command.commands;

import ex05.ConsoleHelper;
import ex05.command.Command;
import ex05.service.TransactionsService;
import ex05.user.User;

public class AddUser implements Command {
    private final TransactionsService service;

    public AddUser(TransactionsService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        ConsoleHelper.writeMessage("Enter a user name and a balance");
        String[] input = ConsoleHelper.readString().split(" ");
        String name = input[0];
        if (input.length != 2)
            throw new IllegalArgumentException("Неверное количество аргументов");
        double balance;
        try {
            balance = Double.parseDouble(input[1]);
            User user = new User(name, balance);
            service.addUser(user);
            ConsoleHelper.writeMessage("User with id = " + user.getIdentifier() + " is added");
        } catch (NumberFormatException exception) {
            ConsoleHelper.writeMessage("Баланс введен не верно");
        }
    }
}
