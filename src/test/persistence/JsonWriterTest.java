package persistence;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Account;
import model.Manager;
import model.Transaction;

// JsonWriterTest class tests the implementation of JsonWriter class
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
        try {
            Manager mg = new Manager();
            ArrayList<Account> accounts1 = mg.getAccounts();
            ArrayList<Transaction> history1 = mg.getHistory();
            assertEquals(0, accounts1.size());
            assertEquals(0, history1.size());
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyManager() {
        JsonWriter writer = new JsonWriter("./data/testWriterEmptyManager.json");
        JsonReader reader = new JsonReader("./data/testWriterEmptyManager.json");
        try {
            Manager mg = new Manager();
            writer.open();
            writer.write(mg);
            writer.close();

            mg = reader.read();
            ArrayList<Account> accounts1 = mg.getAccounts();
            ArrayList<Transaction> history1 = mg.getHistory();
            assertEquals(0, accounts1.size());
            assertEquals(0, history1.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralManager() {
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralManager.json");
        JsonReader reader = new JsonReader("./data/testWriterGeneralManager.json");
        try {
            Manager mg = new Manager();
            mg.addAccount("Nam", 11111, "CAD");
            mg.addAccount("Franklin", 22222, "CAD");
            mg.addTransaction("Nam", 11111, 100.0, "TF", 12345);
            mg.addTransaction("Franklin", 22222, 200.0, "TS", 23456);
            writer.open();
            writer.write(mg);
            writer.close();

            mg = reader.read();
            ArrayList<Account> accounts1 = mg.getAccounts();
            ArrayList<Transaction> history1 = mg.getHistory();
            assertEquals(2, accounts1.size());
            assertEquals(2, history1.size());
            checkAccount("Nam", 11111, "CAD", 0, false, true, accounts1.get(0));
            checkAccount("Franklin", 22222, "CAD", 0, false, true, accounts1.get(1));
            checkTransaction("Nam", 11111, 100.0, "TF", 12345, history1.get(0));
            checkTransaction("Franklin", 22222, 200.0, "TS", 23456, history1.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
