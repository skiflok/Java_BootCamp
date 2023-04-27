package ex05.command.commands;

import ex05.ConsoleHelper;
import ex05.command.Command;
import ex05.service.TransactionsService;
import ex05.user.UserNotFoundException;

public class PerformTransfer implements Command {

    private final TransactionsService service;

    public PerformTransfer(TransactionsService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        ConsoleHelper.writeMessage("Enter a sender ID, a recipient ID, and a transfer amount");
        String [] input = ConsoleHelper.readString().trim().split(" ");
        if (input.length != 3)
            throw new IllegalArgumentException("Неверное количество аргументов");
        int senderID;
        int recipientID;
        double transferAmount;

        try {
            senderID = Integer.parseInt(input[0]);
            recipientID = Integer.parseInt(input[1]);
            transferAmount = Double.parseDouble(input[2]);
            service.transactionExecution(recipientID, senderID, transferAmount);
            ConsoleHelper.writeMessage("The transfer is completed");
        } catch (NumberFormatException e) {
            ConsoleHelper.writeMessage("Некоректный ввод");
        } catch (Exception e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }
    }
}
