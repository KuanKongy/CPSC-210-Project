package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Manager class represents a manager that manages accounts and transactions
// with list of accounts and history of transactions
public class Manager implements Writable {
    private ArrayList<Account> accounts;        // list of my accounts
    private ArrayList<Transaction> history;     // history of transactions

    // EFFECTS: instantiates a manager object with empty list of accounts 
    //          and empty history of transactions
    public Manager() {
        this.accounts = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    // REQUIRES: number is of length 5, currency is either "CAD" or "USD"
    //           owner has a non-zero length
    // MODIFIES: this
    // EFFECTS: creates an account with given owner's name, given preferred number,
    //          given preferred currency, with 'opened' status, intial balance 0 and paying fees
    //          then adds that account into accounts, then returns that account
    public Account addAccount(String owner, int number, String currency) {
        Account account = new Account(owner, number, currency);
        this.accounts.add(account);
        EventLog.getInstance().logEvent(new Event("Added account " + account + " to list of accounts"));
        return account;
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

    // REQUIRES: index < accounts.size(), accounts is not empty, number and id is of length 5,
    //           type is "TS" or "TF" or "WD", 0 < amount <= balance of the account,
    //           receiver has a non-zero length
    // MODIFIES: this, account
    // EFFECTS: selects account on the index's position in the accounts and
    //          makes a transaction of the given type and id on that account 
    //          to the given receiver of given number by given amount,
    //          then adds that transaction into the history,
    //          then returns that transaction
    public Transaction makeTransaction(int index, String receiver, int number, double amount, String type, int id) {
        Account account = selectAccount(index);
        account.makeTransaction(amount);
        Transaction transaction = new Transaction(receiver, number, amount, type, id);
        this.history.add(transaction);
        EventLog.getInstance().logEvent(new Event("Added transaction " + transaction + " to history"));
        return transaction;
    }

    // REQUIRES: number and id is of length 5, receiver has a non-zero length
    //           type is "TS" or "TF" or "WD", 0 < amount,
    // MODIFIES: this
    // EFFECTS: creates a transaction of the given type and id on that account 
    //          to the given receiver of given number by given amount,
    //          then adds that transaction into the history,
    //          then returns that transaction
    public Transaction addTransaction(String receiver, int number, double amount, String type, int id) {
        Transaction transaction = new Transaction(receiver, number, amount, type, id);
        this.history.add(transaction);
        EventLog.getInstance().logEvent(new Event("Added transaction " + transaction + " to history"));
        return transaction;
    }

    // REQUIRES: type is "TS" or "TF" or "WD"
    // EFFECTS: returns the history of transactions sorted into type:
    //          "TS" or "TF" or "WD"
    public ArrayList<Transaction> sortType(String type) {
        ArrayList<Transaction> collection = new ArrayList<>();
        for (Transaction tr : this.history) {
            if (type.equals(tr.getType())) {
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
        Account account = selectAccount(index);
        account.changeStatus(true);
        this.accounts.remove(index);
        EventLog.getInstance().logEvent(new Event("Account " + account + " removed from list of accounts"));
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accounts", accountsToJson());
        json.put("history", historyToJson());
        return json;
    }

    // EFFECTS: returns accounts in this manager as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account a : accounts) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns transactions in this manager as a JSON array
    private JSONArray historyToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : history) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
