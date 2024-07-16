package model;

// Transaction class represents transactions, transfers and withdrawals
// with amount of money it takes away, receiver's name, receiver's accounts's number
// and type of the transaction, which is "TS" as a transaction or "TF" as a transfer 
// or "WD" as a withdrawal
public class Transaction {
    private double amount;      // amount of money the transaction holds
    private String receiver;    // receiver's name
    private int number;         // receiver's account number
    private String type;        // type of the transaction

    // REQUIRES: type is "TS" or "TF" or "WD", number is of length 5,
    //           amount <= balance of the account that pays,
    //           receiver has a non-zero length
    // EFFECTS: creates a transactions with receiver's name, receiver's account number,
    //          with type "TS" or "TF" or "WD" by amount
    public Transaction(String receiver, int number, double amount, String type) {
        this.amount = amount;
        this.receiver = receiver;
        this.number = number;
        this.type = type;
    }

    // EFFECTS: returns transaction's amount
    public double getAmount() {
        return this.amount;
    }

    // EFFECTS: returns transaction's receiver's number
    public int getNumber() {
        return this.number;
    }

    // EFFECTS: returns transaction's receiver
    public String getReceiver() {
        return this.receiver;
    }

    // EFFECTS: returns transaction's type
    public String getType() {
        return this.type;
    }

}
