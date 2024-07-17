package ui;

import java.util.ArrayList;
import java.util.Scanner; // import the scanner class

import model.Manager;
import model.Transaction;
import model.Account;

public class BankManagementApp {
    private Manager manager;
    private Scanner scanner; // this will be your scanner object
    private boolean isRunning;
    private boolean stillViewingAcc;
    private boolean stillViewingHis;
    
    public BankManagementApp() {
        init();

        printDivider();
        System.out.println("Welcome to the Bank Management app!");

        while (this.isRunning) {
            runBankManager();
        }
    }

    public void init() {
        this.manager = new Manager();
        this.scanner = new Scanner(System.in); // initialize your scanner
        this.isRunning = true;
    }

    public void runBankManager() {
        displayMenu();
        String command = this.scanner.nextLine();
        handleMenuCommands(command);
    }
    
    public void displayMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("a: Create and add account");
        System.out.println("l: Accounts");
        System.out.println("t: Transactions");
        System.out.println("q: Exit the application");
        printDivider();
    }

    public void handleMenuCommands(String command) {
        switch (command) {
            case "a":
                addAccount();
                break;
            case "l":
                viewAccounts();
                break;
            case "t":
                viewTransactions();
                break;
            case "q":
                quitApplication();
                break;
            default:
                printDivider();
                System.out.println("Invalid command. Try again.");
        }
    }

    public void addAccount() {
        printDivider();
        System.out.println("Please enter accounts's owner's name:");
        String owner = this.scanner.nextLine();
        printDivider();
        System.out.println("Please enter preferred accounts's number (5 digits):");
        String number = this.scanner.nextLine();
        int accNumber = Integer.parseInt(number);
        printDivider();
        System.out.println("Please enter preferred accounts's currency (CAD or USD):");
        String currency = this.scanner.nextLine();

        this.manager.addAccount(owner, accNumber, currency);

        printDivider();
        System.out.println("Account was added to the list of accounts!");
    }

    public void viewAccounts() {
        this.stillViewingAcc = true;
        while (this.stillViewingAcc) {
            ArrayList<Account> collection = this.manager.getAccounts();
            int i = 1;
            printDivider();
            System.out.println("Accounts:");
            for (Account account: collection) {
                System.out.println(i + ". " + account.getNumber());
                i++;
            }

            displayViewAccMenu();
            String command = this.scanner.nextLine();
            handleViewAccCommands(command);
        }
    }

    public void displayViewAccMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("v: Select account and view its details");
        System.out.println("c: Select account and change its details (fees or currency)");
        System.out.println("t: Select account and make transactions");
        System.out.println("d: Select account and make deposit");
        System.out.println("r: Select account, close and remove it");
        System.out.println("b: Return back to previous menu");
        printDivider();
    }

    public void handleViewAccCommands(String command) {
        switch (command) {
            case "v":
                viewAccountDetails();
                break;
            case "c":
                changeAccountDetails();
                break;
            case "t":
                makeAccTransactions();
                break;
            case "d":
                makeAccDeposit();
                break;
            case "r":
                removeAccount();
                break;
            case "b":
                returnView();
                break;
            default:
                printDivider();
                System.out.println("Invalid command. Try again.");
        }
    }

    public void viewAccountDetails() {
        printDivider();
        System.out.println("Please select account (insert its number):");
        String number = this.scanner.nextLine();
        int accNumber = Integer.parseInt(number);
        ArrayList<Account> collection = this.manager.getAccounts();
        for (Account account: collection) {
            if (account.getNumber() == accNumber) {
                printDivider();
                System.out.println("Account's number: " + account.getNumber());
                System.out.println("Account's owner: " + account.getName());
                System.out.println("Account's currency: " + account.checkCurrency());
                System.out.println("Account's balance: " + account.checkBalance());
                System.out.println("Is account closed? " + account.isClosed());
                System.out.println("Does account pay fees?: " + account.haveFees());
                return;
            }
        }
        printDivider();
        System.out.println("Sorry, account hasn't been found");
    }

    public void changeAccountDetails() {
        printDivider();
        System.out.println("Please select account (insert its number):");
        String number = this.scanner.nextLine();
        int accNumber = Integer.parseInt(number);
        printDivider();
        System.out.println("Please select new currency (CAD or USD):");
        String currency = this.scanner.nextLine();
        printDivider();
        System.out.println("Do you want to pay fees? (Yes or No):");
        String payFees = this.scanner.nextLine();
        boolean hasFees = false;
        if (payFees.equals("Yes")) {
            hasFees = true;
        }
        changeDetails(accNumber, currency, hasFees);
    }

    public void changeDetails(int accNumber, String currency, boolean hasFees) {
        ArrayList<Account> collection = this.manager.getAccounts();
        for (Account account: collection) {
            if (account.getNumber() == accNumber) {
                account.changeCurrency(currency);
                account.changeFees(hasFees);
                printDivider();
                System.out.println("Details have been changed!");
                return;
            }
        }
        printDivider();
        System.out.println("Sorry, account hasn't been found");
    }

    public void makeAccTransactions() {
        printDivider();
        System.out.println("Please select account (insert its number):");
        String number = this.scanner.nextLine();
        int accNumber = Integer.parseInt(number);
        printDivider();
        System.out.println("Please enter receiver's name:");
        String receiver = this.scanner.nextLine();
        printDivider();
        System.out.println("Please enter receiver's account number (5 digits):");
        String numberRec = this.scanner.nextLine();
        int recNumber = Integer.parseInt(numberRec);
        printDivider();
        System.out.println("Please enter amount you want to send:");
        String amountRec = this.scanner.nextLine();
        double amount = Double.parseDouble(amountRec);
        printDivider();
        System.out.println("Please enter this transaction's type (TS, TF or WD):");
        String type = this.scanner.nextLine();
        printDivider();
        System.out.println("Please enter preferred transaction's id (5 digits):");
        String numberId = this.scanner.nextLine();
        int id = Integer.parseInt(numberId);
        createTransaction(accNumber, receiver, recNumber, amount, type, id);
    }

    public void createTransaction(int accNumber, String receiver, int recNumber, double amount, String type, int id) {
        ArrayList<Account> collection = this.manager.getAccounts();
        int i = 0;
        for (Account account: collection) {
            if (account.getNumber() == accNumber) {
                this.manager.makeTransaction(i, receiver, recNumber, amount, type, id);
                printDivider();
                System.out.println("Transaction has been created!");
                return;
            } else {
                i++;
            }
        }
        printDivider();
        System.out.println("Sorry, account hasn't been found");
    }

    public void makeAccDeposit() {
        printDivider();
        System.out.println("Please select account (insert its number):");
        String number = this.scanner.nextLine();
        int accNumber = Integer.parseInt(number);
        printDivider();
        System.out.println("Please enter amount you want to deposit:");
        String amount = this.scanner.nextLine();
        double amountRec = Double.parseDouble(amount);
        ArrayList<Account> collection = this.manager.getAccounts();
        for (Account account: collection) {
            if (account.getNumber() == accNumber) {
                account.makeDeposit(amountRec);
                printDivider();
                System.out.println("Deposit has been made!");
                return;
            }
        }
        printDivider();
        System.out.println("Sorry, account hasn't been found");
    }

    public void removeAccount() {
        printDivider();
        System.out.println("Please select account (insert its number):");
        String number = this.scanner.nextLine();
        int accNumber = Integer.parseInt(number);
        ArrayList<Account> collection = this.manager.getAccounts();
        int i = 0;
        for (Account account: collection) {
            if (account.getNumber() == accNumber) {
                this.manager.remove(i);
                printDivider();
                System.out.println("Account has been removed!");
                return;
            } else {
                i++;
            }
        }
        printDivider();
        System.out.println("Sorry, account hasn't been found");
    }

    public void returnView() {
        this.stillViewingAcc = false;
    }

    public void viewTransactions() {
        this.stillViewingHis = true;
        while (this.stillViewingHis) {
            ArrayList<Transaction> collection = this.manager.getHistory();
            int i = 1;
            printDivider();
            System.out.println("Transactions:");
            for (Transaction transaction: collection) {
                System.out.println(i + ". " + transaction.getId() + " " + transaction.getType());
                i++;
            }
            displayViewHisMenu();
            String command = this.scanner.nextLine();
            handleViewHisCommands(command);
        }
    }

    public void displayViewHisMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("v: Select transaction and view its details");
        System.out.println("s: Select type and view sorted history of transaction");
        System.out.println("b: Return back to previous menu");
        printDivider();
    }

    public void handleViewHisCommands(String command) {
        switch (command) {
            case "v":
                viewTransactionDetails();
                break;
            case "s":
                sortTransactions();
                break;
            case "b":
                returnBack();
                break;
            default:
                printDivider();
                System.out.println("Invalid command. Try again.");
        }
    }

    public void viewTransactionDetails() {
        printDivider();
        System.out.println("Please select transaction (insert its id):");
        String numberId = this.scanner.nextLine();
        int id = Integer.parseInt(numberId);
        ArrayList<Transaction> collection = this.manager.getHistory();
        for (Transaction transaction: collection) {
            if (transaction.getId() == id) {
                printDivider();
                System.out.println("Transaction's number: " + transaction.getId());
                System.out.println("Transaction's amount: " + transaction.getAmount());
                System.out.println("Transaction's receiver: " + transaction.getReceiver());
                System.out.println("Transaction's receiver's number: " + transaction.getNumber());
                System.out.println("Transaction's type: " + transaction.getType());
                return;
            }
        }
        printDivider();
        System.out.println("Sorry, transaction hasn't been found");
    }

    public void sortTransactions() {
        printDivider();
        System.out.println("Please select type you want to sort by (TS or TF or WD):");
        String type = this.scanner.nextLine();
        ArrayList<Transaction> collection = this.manager.sortType(type);
        if (0 == collection.size()) {
            printDivider();
            System.out.println("Sorry, no transactions of this type have been found.");
            return;
        }
        int i = 1;
        printDivider();
        System.out.println("Transactions of type " + type + ":");
        for (Transaction transaction: collection) {
            System.out.println(i + ". " + transaction.getNumber() + " ");
            i++;
        }
    }

    public void returnBack() {
        this.stillViewingHis = false;
    }

    public void quitApplication() {
        this.isRunning = false;
        printDivider();
        System.out.println("Bye! Have a great time!");
        printDivider();
    }

    // EFFECTS: prints out a line of dashes to act as a divider
    private void printDivider() {
        System.out.println("------------------------------------");
    }
}
