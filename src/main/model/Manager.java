package model;

import java.util.ArrayList;

// Manager class represents a manager that manages accounts and transactions
// with list of accounts and history of transactions
public class Manager {
    private ArrayList<Account> accounts;        // list of my accounts
    private ArrayList<Transaction> history;     // history of transactions

    // EFFECTS: instantiates a manager object with list of accounts 
    //          and history of transactions
    public Manager() {
        this.accounts = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    // REQUIRES: number is of length 5, currency is either "CAD" or "USD"
    //           owner has a non-zero length
    // MODIFIES: this
    // EFFECTS: creates an account with the owner's name, preferred number,
    //          preferred currency, then adds that account into accounts
    public void addAccount(String owner, int number, String currency) {
        Account account = new Account(owner, number, currency);
        this.accounts.add(account);
    }

    // REQUIRES: index < accounts.size(), accounts is not empty
    // EFFECTS: returns the selected account on the index's position in the accounts
    public Account selectAccount(int index) {
        return this.accounts.get(index);
    }

    // REQUIRES: index < history.size(), history is not empty
    // EFFECTS: returns the selected transaction on the index's position in the history
    public Transaction selectTransaction(int index) {
        return this.history.get(index);
    }

    // REQUIRES: index < accounts.size(), accounts is not empty, number is of length 5,
    //           type is "TS" or "TF" or "WD", amount <= balance of the account,
    //           receiver has a non-zero length
    // MODIFIES: this, account
    // EFFECTS: selects account on the index's position in the accounts
    //          makes a transaction of the type's type on that account 
    //          to the receiver of account's number by amount
    //          then adds that transaction into the history
    public void makeTransaction(int index, String receiver, int number, double amount, String type) {
        Account account = this.accounts.get(index);
        account.makeTransaction(amount);
        Transaction transaction = new Transaction(receiver, number, amount, type);
        this.history.add(transaction);
    }

    // REQUIRES: type is "TS" or "TF" or "WD"
    // EFFECTS: returns the history of transactions sorted into type:
    //          "TS" or "TF" or "WD"
    public ArrayList<Transaction> sortType(String type) {
        ArrayList<Transaction> collection = new ArrayList<>();
        for (Transaction tr : this.history) {
            if (tr.getType() == type) {
                collection.add(tr);
            }
        }
        return collection;
    }

    // REQUIRES: index < accounts.size(), accounts is not empty
    // MODIFIES: this, account
    // EFFECTS: closes account on the index's position in the accounts
    //          then removes that account from the accounts
    public void remove(int index) {
        Account account = this.accounts.get(index);
        account.changeStatus(true);
        this.accounts.remove(index);
    }

    // EFFECTS: returns list of accounts
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    // EFFECTS: returns history of transactions
    public ArrayList<Transaction> getHistory() {
        return this.history;
    }

    // EFFECTS: returns true if accounts contain account
    public boolean accountsContains(Account account) {
        return this.accounts.contains(account);
    }

    // EFFECTS: returns true if history contains transaction
    public boolean historyContains(Transaction transaction) {
        return this.history.contains(transaction);
    }

    // EFFECTS: returns size of the list of accounts
    public int accountsSize() {
        return this.accounts.size();
    }

    // EFFECTS: returns size of the history
    public int historySize() {
        return this.history.size();
    }
}
