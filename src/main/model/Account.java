package model;

import org.json.JSONObject;

import persistence.Writable;

// Account class represents a bank account with preferred number, owner's name,
// preferred currency CAD or USD, opened/closed status, paying/cancelled fees, balance
public class Account implements Writable {
    // private static final double FEES = 1.25;                // management fees
    private static final double CAD_MULTIPLIER = 1.37;      // multiplier to CAD from USD
    private static final double USD_MULTIPLIER = 0.73;      // ultiplier to USD from CAD
    private double balance;                                 // money balance
    private boolean isClosed;                               // opened/closed account status
    private int number;                                     // account number
    private String name;                                    // owner's name
    private String currency;                                // curreny of account's balance
    private boolean haveFees;                               // paying/cancelled fees status

    // REQUIRES: owner has a non-zero length, number is of length 5
    //           currency is either "CAD" or "USD"
    // EFFECTS: creates a bank account with given owner's name
    //          with given preffered account number and given currency
    //          with 'opened' status, intial balance 0 and paying fees
    public Account(String owner, int number, String currency) {
        this.name = owner;
        this.number = number;
        this.isClosed = false;
        this.currency = currency;
        this.balance = 0;
        this.haveFees = true;
    }
    
    // REQUIRES: deposit > 0
    // MODIFIES: this
    // EFFECTS: increases the balance by deposit's amount
    public void makeDeposit(double deposit) {
        this.balance = this.balance + deposit;
    }

    // REQUIRES: 0 < amount <= balance
    // MODIFIES: this
    // EFFECTS: decreases the balance by amount's amount
    public void makeTransaction(double amount) {
        this.balance = this.balance - amount;
    }

    // MODIFIES: this
    // EFFECTS: chages this.isClosed status to status
    public void changeStatus(boolean status) {
        this.isClosed = status;
    }

    // MODIFIES: this
    // EFFECTS: chages this.haveFees status to haveFees
    public void changeFees(boolean haveFees) {
        this.haveFees = haveFees;
    }

    // REQUIRES: currency is either "CAD" or "USD",
    //           currency is not the same as current one
    // MODIFIES: this
    // EFFECTS: changes this.currency to currency
    //          and converts balance into that currency's values
    public void changeCurrency(String currency) {
        this.currency = currency;
        switch (currency) {
            case "CAD":
                this.balance = this.balance * CAD_MULTIPLIER;
                break;
            case "USD":
                this.balance = this.balance * USD_MULTIPLIER;
                break;
            default:
        }
    }

    // EFFECTS: returns account's balance
    public double checkBalance() {
        return this.balance;
    }

    // EFFECTS: returns account's balance's currency
    public String checkCurrency() {
        return this.currency;
    }

    // EFFECTS: returns account's owner's name
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns account's number
    public int getNumber() {
        return this.number;
    }

    // EFFECTS: returns account's isClosed status
    public boolean isClosed() {
        return this.isClosed;
    }

    // EFFECTS: returns account's haveFees status
    public boolean haveFees() {
        return this.haveFees;
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        String number = Integer.toString(this.number);
        String balance = Double.toString(this.balance);
        String isClosed = Boolean.toString(this.isClosed);
        String haveFees = Boolean.toString(this.haveFees);
        json.put("name", this.name);
        json.put("number", number);
        json.put("currency", this.currency);
        json.put("balance", balance);
        json.put("isClosed", isClosed);
        json.put("haveFees", haveFees);
        return json;
    }
}
