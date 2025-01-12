package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TransactionTest class tests the implementation of Transaction class
public class TransactionTest {
    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void runBefore() {
        transaction1 = new Transaction("Jimmy", 23456, 100, "TS", 11111);
        transaction2 = new Transaction("Bobby", 67456, 400, "TF", 22222);
    }

    @Test
    void testConstructor() {
        assertEquals(100, transaction1.getAmount());
        assertEquals("Jimmy", transaction1.getReceiver());
        assertEquals(23456, transaction1.getNumber());
        assertEquals("TS", transaction1.getType());
        assertEquals(11111, transaction1.getId());
        assertEquals(400, transaction2.getAmount());
        assertEquals("Bobby", transaction2.getReceiver());
        assertEquals(67456, transaction2.getNumber());
        assertEquals("TF", transaction2.getType());
        assertEquals(22222, transaction2.getId());
    }

    @Test
    void testToSting() {
        assertEquals("11111 TS", transaction1.toString());
        assertEquals("22222 TF", transaction2.toString());
    }
}
