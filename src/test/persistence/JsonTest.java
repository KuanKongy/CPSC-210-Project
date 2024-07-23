package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Account;
import model.Transaction;

// JsonTest class tests the implementation of Json classes
public class JsonTest {
    protected void checkAccount(String own, int num, String cur, double bal, boolean is, boolean hav, Account acc) {
        assertEquals(own, acc.getName());
        assertEquals(num, acc.getNumber());
        assertEquals(cur, acc.checkCurrency());
        assertEquals(bal, acc.checkBalance());
        assertEquals(is, acc.isClosed());
        assertEquals(hav, acc.haveFees());
    }

    protected void checkTransaction(String rec, int num, double am, String typ, int id, Transaction tran) {
        assertEquals(rec, tran.getReceiver());
        assertEquals(num, tran.getNumber());
        assertEquals(am, tran.getAmount());
        assertEquals(typ, tran.getType());
        assertEquals(id, tran.getId());
    }
}
