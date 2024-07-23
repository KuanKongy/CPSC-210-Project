package model;

import org.json.JSONObject;

import persistence.Writable;

// Transaction class represents transactions, transfers and withdrawals
// with amount of money it takes away, receiver's name, receiver's accounts's number,
// id, type of the transaction, which is "TS" as a transaction or "TF" as a transfer 
// or "WD" as a withdrawal,
public class Transaction implements Writable {
    private double amount;      // amount of money the transaction holds
    private String receiver;    // receiver's name
    private int number;         // receiver's account number
    private String type;        // type of the transaction
    private int id;             // id of the transaction

    // REQUIRES: type is "TS" or "TF" or "WD", number and id is of length 5,
    //           0 < amount <= balance of the account that pays,
    //           receiver has a non-zero length
    // EFFECTS: creates a transactions with given receiver's name, given receiver's account number,
    //          with given id, with given type "TS" or "TF" or "WD" by given amount
    public Transaction(String receiver, int number, double amount, String type, int id) {
        this.amount = amount;
        this.receiver = receiver;
        this.number = number;
        this.type = type;
        this.id = id;
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

    // EFFECTS: returns transaction's id
    public int getId() {
        return this.id;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        String number = Integer.toString(this.number);
        String id = Integer.toString(this.id);
        String amount = Double.toString(this.amount);
        json.put("receiver", this.receiver);
        json.put("number", number);
        json.put("amount", amount);
        json.put("type", this.type);
        json.put("id", id);
        return json;
    }
}
