package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// ManagerTest class tests the implementation of Manager class
public class ManagerTest {
    private Manager manager1;

    @BeforeEach
    void runBefore() {
        manager1 = new Manager();
    }

    @Test
    void testConstructor() {
        assertEquals(0, manager1.accountsSize());
        assertEquals(0, manager1.historySize());
    }

    @Test
    void testAddAccount() {
        Account account1 = manager1.addAccount("Jimmy Bill Bob", 65432, "USD");
        assertEquals("Jimmy Bill Bob", account1.getName());
        assertEquals(65432, account1.getNumber());
        assertEquals("USD", account1.checkCurrency());
        assertTrue(manager1.accountsContains(account1));
        assertEquals(1, manager1.accountsSize());
        Account account2 = manager1.addAccount("Jim", 92834, "CAD");
        assertTrue(manager1.accountsContains(account2));
        assertEquals(2, manager1.accountsSize());
    }

    @Test
    void testSelectAccount() {
        Account account1 = manager1.addAccount("Billy Jim", 82846, "USD");
        Account account2 = manager1.selectAccount(0);
        assertEquals(account1, account2);
        Account account3 = manager1.addAccount("Bobbyy Jim", 61492, "CAD");
        Account account4 = manager1.selectAccount(1);
        assertEquals(account3, account4);
    }

    @Test
    void testSelectTransaction() {
        Account account1 = manager1.addAccount("Billy Jim Bob", 48962, "CAD");
        Account account2 = manager1.addAccount("Bobbyy Jim", 61492, "CAD");
        account1.makeDeposit(1000);
        account2.makeDeposit(400);
        Transaction transaction1 = manager1.makeTransaction(0, "Bobby Bob Bob", 92435, 200, "TS", 11111);
        Transaction transaction2 = manager1.makeTransaction(1, "Bobby Bob", 12408, 300, "TF", 22222);
        Transaction transaction3 = manager1.selectTransaction(0);
        assertEquals(transaction1, transaction3);
        Transaction transaction4 = manager1.selectTransaction(1);
        assertEquals(transaction2, transaction4);
    }

    @Test
    void testMakeTransaction() {
        Account account1 = manager1.addAccount("Billy Jim Bob", 48962, "CAD");
        Account account2 = manager1.addAccount("Bobbyy Jim", 61492, "CAD");
        account1.makeDeposit(1000);
        account2.makeDeposit(400);
        Transaction transaction1 = manager1.makeTransaction(0, "Bobby Bob Bob", 92435, 200, "TS", 11111);
        assertEquals(800, account1.checkBalance());
        assertEquals(200, transaction1.getAmount());
        assertEquals("Bobby Bob Bob", transaction1.getReceiver());
        assertEquals(92435, transaction1.getNumber());
        assertEquals("TS", transaction1.getType());
        assertEquals(11111, transaction1.getId());
        assertTrue(manager1.historyContains(transaction1));
        assertEquals(1, manager1.historySize());
        Transaction transaction2 = manager1.makeTransaction(1, "Bobby Bob", 12408, 300, "TF", 22222);
        assertEquals(100, account2.checkBalance());
        assertTrue(manager1.historyContains(transaction2));
        assertEquals(2, manager1.historySize());
    }

    @Test
    void testAddTransaction() {
        Transaction transaction1 = manager1.addTransaction("Nam", 11111, 100.0, "TF", 12345);
        assertEquals(100, transaction1.getAmount());
        assertEquals("Nam", transaction1.getReceiver());
        assertEquals(11111, transaction1.getNumber());
        assertEquals("TF", transaction1.getType());
        assertEquals(12345, transaction1.getId());
        assertEquals(1, manager1.historySize());
        assertEquals(transaction1, manager1.getHistory().get(0));
        Transaction transaction2 = manager1.addTransaction("Franklin", 22222, 200.0, "TS", 23456);
        assertEquals(200, transaction2.getAmount());
        assertEquals("Franklin", transaction2.getReceiver());
        assertEquals(22222, transaction2.getNumber());
        assertEquals("TS", transaction2.getType());
        assertEquals(23456, transaction2.getId());
        assertEquals(2, manager1.historySize());
        assertEquals(transaction2, manager1.getHistory().get(1));
    }

    @Test
    void testSortType() {
        Account account1 = manager1.addAccount("Billy Jim Bob", 48962, "CAD");
        Account account2 = manager1.addAccount("Bobbyy Jim", 61492, "CAD");
        account1.makeDeposit(1000);
        account2.makeDeposit(400);
        Transaction transaction1 = manager1.makeTransaction(0, "Bobby Bob Bob", 92435, 200, "TS", 11111);
        manager1.makeTransaction(1, "Bobby Bob", 12408, 300, "TF", 33333);
        manager1.makeTransaction(1, "Bobby Bob", 12408, 100, "TF", 22222);
        ArrayList<Transaction> collection1 = manager1.sortType("TS");
        ArrayList<Transaction> collection2 = manager1.sortType("TF");
        ArrayList<Transaction> collection3 = manager1.sortType("WD");
        assertEquals(3, manager1.historySize());
        assertEquals(1, collection1.size());
        assertEquals(2, collection2.size());
        assertEquals(0, collection3.size());
        assertTrue(collection1.contains(transaction1));
        assertFalse(collection2.contains(transaction1));
    }

    @Test
    void testRemove() {
        Account account1 = manager1.addAccount("Billy Jim Bob", 48962, "CAD");
        Account account2 = manager1.addAccount("Bobbyy Jim", 61492, "CAD");
        manager1.remove(0);
        assertTrue(account1.isClosed());
        assertFalse(manager1.accountsContains(account1));
        assertEquals(1, manager1.accountsSize());
        manager1.remove(0);
        assertTrue(account2.isClosed());
        assertFalse(manager1.accountsContains(account2));
        assertEquals(0, manager1.accountsSize());
    }

    @Test
    void testGetAccounts() {
        Account account1 = manager1.addAccount("Billy Jim Bob", 48962, "CAD");
        ArrayList<Account> collection1 = manager1.getAccounts();
        assertTrue(collection1.contains(account1));
        assertEquals(1, collection1.size());
    }

    @Test
    void testGetHistory() {
        Account account1 = manager1.addAccount("Billy Jim Bob", 48962, "CAD");
        account1.makeDeposit(1000);
        Transaction transaction1 = manager1.makeTransaction(0, "Bobby Bob Bob", 92435, 200, "TS", 55555);
        ArrayList<Transaction> collection1 = manager1.getHistory();
        assertTrue(collection1.contains(transaction1));
        assertEquals(1, collection1.size());
    }
}
