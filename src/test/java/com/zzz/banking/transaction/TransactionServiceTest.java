package com.zzz.banking.transaction;

import com.zzz.banking.account.dataaccessobject.IAccountCollection;
import com.zzz.banking.account.dataobject.AccountDO;
import com.zzz.banking.exception.InSufficientBalanceException;
import com.zzz.banking.exception.InvalidTransferAmountException;
import com.zzz.banking.exception.NoAccountFoundException;
import com.zzz.banking.exception.SameAccountsException;
import com.zzz.banking.transaction.dataaccessobject.ITransactionCollection;
import com.zzz.banking.transaction.dataobject.TransactionDO;
import com.zzz.banking.transaction.service.ITransactionService;
import com.zzz.banking.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    @MockBean
    private IAccountCollection accountCollection;

    @MockBean
    private ITransactionCollection transactionCollection;

    @InjectMocks
    private ITransactionService transactionService;

    private AccountDO sourceAccount;

    @BeforeEach
    public void init() {
        accountCollection = Mockito.mock(IAccountCollection.class);
        transactionCollection = Mockito.mock(ITransactionCollection.class);
        transactionService = new TransactionService(accountCollection, transactionCollection);
        sourceAccount = new AccountDO(Long.valueOf(5555), BigDecimal.valueOf(100.00), "GBP",
                new Date());
        when(accountCollection.findByAccountNumber(Long.valueOf(5555))).thenReturn(Optional.of(sourceAccount));
    }

    @Test
    public void testTransactionNonExistentAccountError() {
        when(accountCollection.findByAccountNumber(Long.valueOf(4444))).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoAccountFoundException.class, () -> {
            transactionService.transferAmount(new Transaction(Long.valueOf(5555), Long.valueOf(4444),
                    BigDecimal.valueOf(10.00), "GBP"));
        });

        String expectedMessage = "Source Account 5555 or Target Account 4444 does not exist.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testTransactionSameAccountError() {
        Exception exception = assertThrows(SameAccountsException.class, () -> {
            transactionService.transferAmount(new Transaction(Long.valueOf(5555), Long.valueOf(5555),
                    BigDecimal.valueOf(10.00), "GBP"));
        });

        String expectedMessage = "Source Account 5555 and Target Account 5555 are same.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testTransactionInsufficientBalanceError() {
        when(accountCollection.findByAccountNumber(Long.valueOf(4444))).thenReturn(Optional.of(new AccountDO()));
        Exception exception = assertThrows(InSufficientBalanceException.class, () -> {
            transactionService.transferAmount(new Transaction(Long.valueOf(5555), Long.valueOf(4444),
                    BigDecimal.valueOf(1000.00), "GBP"));
        });

        String expectedMessage = "Source Account 5555 do not have sufficient balance for this transaction.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testTransactionNegativeTransactionAmount() {
        when(accountCollection.findByAccountNumber(Long.valueOf(4444))).thenReturn(Optional.of(new AccountDO()));
        Exception exception = assertThrows(InvalidTransferAmountException.class, () -> {
            transactionService.transferAmount(new Transaction(Long.valueOf(5555), Long.valueOf(4444),
                    BigDecimal.valueOf(-10.00), "GBP"));
        });

        String expectedMessage = "Transaction amount should be greater than zero";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testTransaction() {
        AccountDO targetAccount = new AccountDO(Long.valueOf(4444), BigDecimal.valueOf(100.00), "GBP",
                new Date());
        when(accountCollection.findByAccountNumber(Long.valueOf(4444))).thenReturn(Optional.of(targetAccount));
        TransactionDO transactionDO = new TransactionDO(Long.valueOf(5555),
                Long.valueOf(4444), BigDecimal.valueOf(10.00), "GBP");
        when(transactionCollection.save(transactionDO)).thenReturn(transactionDO);
//        TransactionResult result = transactionService.transferAmount(new Transaction(Long.valueOf(5555),
//                Long.valueOf(4444), BigDecimal.valueOf(10.00), "GBP"));
//        assertEquals(sourceAccount, result.getSourceAccount());
//        assertEquals(targetAccount, result.getTargetAccount());
    }
}
