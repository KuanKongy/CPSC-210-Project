package persistence;

import model.Manager;
import model.Account;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads manager from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads manager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Manager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses manager from JSON object and returns it
    private Manager parseManager(JSONObject jsonObject) {
        Manager manager = new Manager();
        addAccounts(manager, jsonObject);
        addHistory(manager, jsonObject);
        return manager;
    }

    // MODIFIES: manager
    // EFFECTS: parses accounts from JSON object and adds them to manager
    private void addAccounts(Manager manager, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(manager, nextAccount);
        }
    }

    // MODIFIES: manager
    // EFFECTS: parses account from JSON object and adds it to manager
    private void addAccount(Manager manager, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String currency = jsonObject.getString("currency");
        String number = jsonObject.getString("number");
        int accNumber = Integer.parseInt(number);
        String balance = jsonObject.getString("balance");
        double accBalance = Double.parseDouble(balance);
        String isClosed = jsonObject.getString("isClosed");
        String haveFees = jsonObject.getString("haveFees");
        boolean hasFees = false;
        if (haveFees.equals("true")) {
            hasFees = true;
        }
        boolean hasClosed = false;
        if (isClosed.equals("true")) {
            hasClosed = true;
        }
        Account account =  manager.addAccount(name, accNumber, currency);
        account.changeFees(hasFees);
        account.changeStatus(hasClosed);
        account.makeDeposit(accBalance);
    }

    // MODIFIES: manager
    // EFFECTS: parses history from JSON object and adds them to manager
    private void addHistory(Manager manager, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("history");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(manager, nextTransaction);
        }
    }

    // MODIFIES: manager
    // EFFECTS: parses transaction from JSON object and adds it to manager
    private void addTransaction(Manager manager, JSONObject jsonObject) {
        String receiver = jsonObject.getString("receiver");
        String type = jsonObject.getString("type");
        String number = jsonObject.getString("number");
        int accNumber = Integer.parseInt(number);
        String id = jsonObject.getString("id");
        int idRec = Integer.parseInt(id);
        String amount = jsonObject.getString("amount");
        double amountRec = Double.parseDouble(amount);
        manager.addTransaction(receiver, accNumber, amountRec, type, idRec);
    }
}

