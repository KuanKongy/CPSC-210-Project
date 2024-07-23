package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// AccountTest class tests the implementation of Account class
public class AccountTest {
    private Account account1;
    private Account account2;

    @BeforeEach
    void runBefore() {
        account1 = new Account("Bill Bob", 12345, "CAD");
        account2 = new Account("Billy", 41345, "USD");
    }

    @Test
    void testConstructor() {
        assertEquals(0, account1.checkBalance());
        assertEquals("Bill Bob", account1.getName());
        assertEquals(12345, account1.getNumber());
        assertEquals("CAD", account1.checkCurrency());
        assertTrue(account1.haveFees());
        assertFalse(account1.isClosed());
        assertEquals(0, account2.checkBalance());
        assertEquals("Billy", account2.getName());
        assertEquals(41345, account2.getNumber());
        assertEquals("USD", account2.checkCurrency());
        assertTrue(account2.haveFees());
        assertFalse(account2.isClosed());
    }

    @Test
    void testMakeDeposit() {
        account1.makeDeposit(100);
        assertEquals(100, account1.checkBalance());
        account1.makeDeposit(200);
        assertEquals(300, account1.checkBalance());
    }

    @Test
    void testMakeTransaction() {
        account1.makeDeposit(1000);
        account1.makeTransaction(400);
        assertEquals(600, account1.checkBalance());
        account1.makeTransaction(100);
        assertEquals(500, account1.checkBalance());
    }

    @Test
    void testChangeStatus() {
        account1.changeStatus(false);
        assertFalse(account1.isClosed());
        account1.changeStatus(true);
        assertTrue(account1.isClosed());
    }

    @Test
    void testChangeFees() {
        account1.changeFees(true);
        assertTrue(account1.haveFees());
        account1.changeFees(false);
        assertFalse(account1.haveFees());
    }

    @Test
    void testChangeCurrency() {
        account1.makeDeposit(1000);
        account1.changeCurrency("USD");
        assertEquals("USD", account1.checkCurrency());
        assertEquals(730, account1.checkBalance());
        account1.changeCurrency("CAD");
        assertEquals("CAD", account1.checkCurrency());
        assertEquals(1000.1, account1.checkBalance());
    }
}
