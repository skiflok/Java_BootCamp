package ex05;

import ex05.command.Command;
import ex05.command.commands.*;
import ex05.service.TransactionsService;

public class Menu {
    private final Command addUser;
    private final Command viewUserBalance;
    private final Command performTransfer;
    private final Command viewAllTransactionsForSpecificUser;
    private final Command devRemoveTransferByID;
    private final Command devCheckTransferValidity;
    private final boolean dev;
    private boolean isWork = true;

    private final TransactionsService service;

    public Menu(boolean dev) {
        this.service = new TransactionsService();
        addUser = new AddUser(service);
        viewUserBalance = new ViewUserBalance(service);
        performTransfer = new PerformTransfer(service);
        viewAllTransactionsForSpecificUser = new ViewAllTransactionsForSpecificUser(service);
        devRemoveTransferByID = new DevRemoveTransferByID(service);
        devCheckTransferValidity = new DevCheckTransferValidity(service);
        this.dev = dev;
    }

    private void printMenu() {
        ConsoleHelper.writeMessage("1. Add a user");
        ConsoleHelper.writeMessage("2. View user balances");
        ConsoleHelper.writeMessage("3. Perform a transfer");
        ConsoleHelper.writeMessage("4. View all transactions for a specific user");
        if (dev) {
            ConsoleHelper.writeMessage("5. DEV - remove a transfer by ID");
            ConsoleHelper.writeMessage("6. DEV - check transfer validity");
        }
        ConsoleHelper.writeMessage("7. Finish execution");
    }

    private String responseRequest() {
        return ConsoleHelper.readString();
    }

    private void execution(String stringCommand) {
        int command = getCommandFromString(stringCommand);
        switch (command) {
            case 1 :
                addUser.execute();
                break;
            case 2 :
                viewUserBalance.execute();
                break;
            case 3 :
                performTransfer.execute();
                break;
            case 4 :
                viewAllTransactionsForSpecificUser.execute();
                break;
            case 5 :
                devRemoveTransferByID.execute();
                break;
            case 6 :
                devCheckTransferValidity.execute();
                break;
            case 7 :
                isWork = false;
                break;
            default:
                ConsoleHelper.writeMessage("Неверная команда");
                break;
        }
    }

    private int getCommandFromString(String inputStringCommand) {
        int command = 0;
        try {
            command = Integer.parseInt(inputStringCommand);
        } catch (NumberFormatException e) {
            ConsoleHelper.writeMessage("Ошибка ввода. Введите номер команды меню");
        }
        return command;
    }

    public void run () {
        while (isWork) {
            printMenu();
            try {
                execution(responseRequest());
            } catch (IllegalArgumentException e) {
                ConsoleHelper.writeMessage(e.getMessage());
                e.printStackTrace();
            }
            System.out.println("---------------------------------------------------------");
        }
    }
}
