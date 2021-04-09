package com.zzz.banking.transaction;

import com.zzz.banking.account.Account;
import com.zzz.banking.account.dataaccessobject.IAccountCollection;
import com.zzz.banking.account.dataaccessobject.InMemoryAccountCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionUtilsTest {

    IAccountCollection accountCollection;

    @BeforeEach()
    public void setup() {
        accountCollection = new InMemoryAccountCollection();
    }

    @Test
    public void testIsSufficientBalanceForTransfer() {
        assertTrue(TransactionUtils.isSufficientBalanceForTransfer(
                Optional.of(new Account(Long.valueOf(5555), BigDecimal.valueOf(3000), "GBP", new Date())),
                BigDecimal.valueOf(100)));

        assertFalse(TransactionUtils.isSufficientBalanceForTransfer(
                Optional.of(new Account(Long.valueOf(5555), BigDecimal.valueOf(3000), "GBP", new Date())),
                BigDecimal.valueOf(10000)));
    }

    @Test
    public void testIsSourceAndTargetAccountSame() {
        assertTrue(TransactionUtils.isSourceAndTargetAccountSame(new Transaction(Long.valueOf(5555), Long.valueOf(5555),
                BigDecimal.valueOf(100.00), "GBP")));

        assertFalse(TransactionUtils.isSourceAndTargetAccountSame(new Transaction(Long.valueOf(5555), Long.valueOf(4444),
                BigDecimal.valueOf(100.00), "GBP")));
    }

    @Test
    public void testTransferMoney() {
        TransactionResult result = TransactionUtils.transferMoney(
                Optional.of(new Account(Long.valueOf(5555), BigDecimal.valueOf(3000), "GBP", new Date())),
                Optional.of(new Account(Long.valueOf(4444), BigDecimal.valueOf(6000), "GBP", new Date())),
                BigDecimal.valueOf(100),
                "GBP"
        );
        assertEquals(result.getSourceAccount().getBalance().doubleValue(), 2900.00);
        assertEquals(result.getTargetAccount().getBalance().doubleValue(), 6100.00);
    }

    @Test
    public void testTransferNegativeMoney() throws ParseException {
        TransactionResult result = TransactionUtils.transferMoney(
                Optional.of(new Account(Long.valueOf(5555), BigDecimal.valueOf(3000), "GBP",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-09"))),
                Optional.of(new Account(Long.valueOf(4444), BigDecimal.valueOf(6000), "GBP",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-09"))),
                BigDecimal.valueOf(-100), "GBP");
        assertEquals(result.getSourceAccount().getBalance().doubleValue(), 3000.00);
        assertEquals(result.getTargetAccount().getBalance().doubleValue(), 6000.00);
    }

}
