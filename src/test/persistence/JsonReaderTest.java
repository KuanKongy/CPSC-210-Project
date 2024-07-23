package persistence;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Account;
import model.Manager;
import model.Transaction;

// JsonReaderTest class tests the implementation of JsonReader class
public class JsonReaderTest extends JsonTest {
    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Manager mg = new Manager();
            ArrayList<Account> accounts1 = mg.getAccounts();
            ArrayList<Transaction> history1 = mg.getHistory();
            assertEquals(0, accounts1.size());
            assertEquals(0, history1.size());
            mg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyManager() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyManager.json");
        try {
            Manager mg = reader.read();
            ArrayList<Account> accounts1 = mg.getAccounts();
            ArrayList<Transaction> history1 = mg.getHistory();
            assertEquals(0, accounts1.size());
            assertEquals(0, history1.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralManager() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralManager.json");
        try {
            Manager mg = reader.read();
            ArrayList<Account> accounts1 = mg.getAccounts();
            ArrayList<Transaction> history1 = mg.getHistory();
            assertEquals(2, accounts1.size());
            assertEquals(2, history1.size());
            checkAccount("Nam", 11111, "CAD", 10000.0, false, true, accounts1.get(0));
            checkAccount("Franklin", 22222, "USD", 20000.0, true, false, accounts1.get(1));
            checkTransaction("Nam", 11111, 100.0, "TF", 12345, history1.get(0));
            checkTransaction("Franklin", 22222, 200.0, "TS", 23456, history1.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
