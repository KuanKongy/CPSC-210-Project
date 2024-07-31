package ui;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Manager;
import model.Transaction;
import model.Account;

// BankManagementApp represents a Bank Management application with console-based UI
// Based the UI on the FlashcardReviewer's interface 
// Based the save/load system on the JsonSerializationDemo's application 
public class BankManagementApp {
    private static final String JSON_STORE = "./data/manager.json"; // relative path to saved/loaded data
    private Manager manager;                                        // manager to manage bank
    private Scanner scanner;                                        // scanner from UI
    private boolean isRunning;                                      // app status
    private boolean stillViewingAcc;                                // accounts menu status
    private boolean stillViewingHis;                                // history menu status
    private JsonWriter jsonWriter;                                  // writer from Json to save data
    private JsonReader jsonReader;                                  // reader from Json to load data
    
    // EFFECTS: instantiates a BankManagementApp's console UI,
    //          initializes fields and prints welcoming message,
    //          then runs bankmanager until told to stop
    public BankManagementApp() {
        init();

        printDivider();
        System.out.println("Welcome to the Bank Management app!");

        while (this.isRunning) {
            runBankManager();
        }
    }

    // MODIFIES: this
    // EFFECTS: instantiates manager, scanner, writer, reader and changes app's status to running
    public void init() {
        this.manager = new Manager();
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu, then takes the user's command to the handler of menu
    public void runBankManager() {
        displayMenu();
        String command = this.scanner.nextLine();
        handleMenuCommands(command);
    }
    
    // EFFECTS: prints out the menu with commands to chose from
    public void displayMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("a: Create and add account");
        System.out.println("c: Accounts");
        System.out.println("t: Transactions");
        System.out.println("s: Save data");
        System.out.println("l: Load data");
        System.out.println("q: Exit the application");
        printDivider();
    }

    // EFFECTS: processes the user's commands, prints invalid if couldn't process
    public void handleMenuCommands(String command) {
        switch (command) {
            case "a":
                addAccount();
                break;
            case "c":
                viewAccounts();
                break;
            case "t":
                viewTransactions();
                break;
            case "s":
                saveData();
                break;
            case "l":
                loadData();
                break;
            case "q":
                quitApplication();
                break;
            default:
                printDivider();
                System.out.println("Invalid command. Try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: reads user's inputs to create and add accounts to the list of accounts
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

    // MODIFIES: this
    // EFFECTS: starts accounts menu and prints list of accounts
    //          displays accounts menu, then takes the user's command to the handler of menu,
    //          until told to return back
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

    // EFFECTS: prints out the menu with commands to chose from
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

    // EFFECTS: processes the user's commands, prints invalid if couldn't process
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

    // EFFECTS: selects an accounts and prints its details, print invalid if couldn't find
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

    // EFFECTS: reads user's inputs to select and change account's details
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

    // REQUIRES: currency is "CAD" or "USD", accNumber is of length 5
    // MODIFIES: account
    // EFFECTS: finds an accounts and chages its currency and hasFees, prints invalid if couldn't find
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

    // EFFECTS: reads user's inputs to select account and make transaction
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

    // REQUIRES: accNumber and recNumber and id are of length 5,
    //           0 < amount <= balance, type is "TS" or "TF or "WD"
    // MODIFIES: this, account
    // EFFECTS: finds an accounts, then makes a transaction, prints sorry if couldn't find
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

    // MODIFIES: accounts
    // EFFECTS: selects account, makes deposit into that account, prints sorry if couldn't find
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

    // MODIFIES: this, account
    // EFFECTS: selects account, removes it from the list of accounts, prints sorry if couldn't find
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

    // MODIFIES: this
    // EFFECTS: closes the accounts menu
    public void returnView() {
        this.stillViewingAcc = false;
    }

    // MODIFIES: this
    // EFFECTS: starts history menu and prints history of transactions
    //          displays history menu, then takes the user's command to the handler of menu,
    //          until told to return back
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

    // EFFECTS: prints out the menu with commands to chose from
    public void displayViewHisMenu() {
        printDivider();
        System.out.println("Please select an option:");
        System.out.println("v: Select transaction and view its details");
        System.out.println("s: Select type and view sorted history of transaction");
        System.out.println("b: Return back to previous menu");
        printDivider();
    }

    // EFFECTS: processes the user's commands, prints invalid if couldn't process
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

    // EFFECTS: selects transaction, prints its details, prints sorry if couldn't find
    public void viewTransactionDetails() {
        printDivider();
        System.out.println("Please select transaction (insert its id):");
        String numberId = this.scanner.nextLine();
        int id = Integer.parseInt(numberId);
        ArrayList<Transaction> collection = this.manager.getHistory();
        for (Transaction transaction: collection) {
            if (transaction.getId() == id) {
                printDivider();
                System.out.println("Transaction's id: " + transaction.getId());
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

    // EFFECTS: selects type and sort the collection by it, prints that collection, prints invalid if couldn't find
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
            System.out.println(i + ". " + transaction.getId() + " ");
            i++;
        }
    }

    // MODIFIES: this
    // EFFECTS: closes the history menu
    public void returnBack() {
        this.stillViewingHis = false;
    }

    // EFFECTS: saves manager to file
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.manager);
            jsonWriter.close();
            printDivider();
            System.out.println("Saved data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            printDivider();
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads manager from file
    private void loadData() {
        try {
            this.manager = jsonReader.read();
            printDivider();
            System.out.println("Loaded data from " + JSON_STORE);
        } catch (IOException e) {
            printDivider();
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: quits BankManagement application
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
