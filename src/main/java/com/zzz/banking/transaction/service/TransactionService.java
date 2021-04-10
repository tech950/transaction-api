package com.zzz.banking.transaction.service;

import com.zzz.banking.account.Account;
import com.zzz.banking.account.dataaccessobject.IAccountCollection;
import com.zzz.banking.account.dataobject.AccountDO;
import com.zzz.banking.exception.InSufficientBalanceException;
import com.zzz.banking.exception.InvalidTransferAmountException;
import com.zzz.banking.exception.NoAccountFoundException;
import com.zzz.banking.exception.SameAccountsException;
import com.zzz.banking.transaction.Transaction;
import com.zzz.banking.transaction.TransactionResult;
import com.zzz.banking.transaction.dataaccessobject.ITransactionCollection;
import com.zzz.banking.transaction.dataobject.TransactionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    private IAccountCollection accountCollection;

    private ITransactionCollection transactionCollection;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(IAccountCollection accountCollection, ITransactionCollection transactionCollection) {
        this.accountCollection = accountCollection;
        this.transactionCollection = transactionCollection;
    }

    /**
     * Transfer money after checking the following conditions * Source Account and Target Account should exist * Source
     * Account and Target Account should not be same * There should be sufficient balance in the source account
     *
     * @param transaction (required)
     * @return
     */
    @Override
    public TransactionResult transferAmount(Transaction transaction) {
        LOGGER.info("Received transaction request");
        Optional<AccountDO> sourceAccount = accountCollection.findByAccountNumber(transaction.getSourceAccountId());
        Optional<AccountDO> targetAccount = accountCollection.findByAccountNumber(transaction.getTargetAccountId());
        if (!sourceAccount.isPresent() || !targetAccount.isPresent()) {
            throw new NoAccountFoundException(transaction.getSourceAccountId(),
                    transaction.getTargetAccountId());
        } else if (transaction.getSourceAccountId().equals(transaction.getTargetAccountId())) {
            throw new SameAccountsException(transaction.getSourceAccountId(),
                    transaction.getTargetAccountId());
        } else if (sourceAccount.get().getBalance().doubleValue() < transaction.getAmount().doubleValue()) {
            throw new InSufficientBalanceException(transaction.getSourceAccountId());
        } else if (transaction.getAmount().doubleValue() <= 0) {
            throw new InvalidTransferAmountException();
        } else {
            TransactionDO savedTransaction = transactionCollection.save(
                    new TransactionDO(transaction.getSourceAccountId(), transaction.getTargetAccountId(),
                            transaction.getAmount(), transaction.getCurrency()));
            return transferMoney(savedTransaction.getId(), sourceAccount.get(), targetAccount.get(),
                    transaction.getAmount(), transaction.getCurrency());
        }
    }

    /**
     * Transfer the specified amount from Source Account to Target Account and returns the result
     */
    public static TransactionResult transferMoney(Long id,
                                                  AccountDO sourceAccount,
                                                  AccountDO targetAccount,
                                                  @NotNull BigDecimal amount,
                                                  @NotNull String currency) {
        sourceAccount.setBalance(BigDecimal.valueOf(sourceAccount.getBalance().doubleValue()
                - amount.doubleValue()));
        LOGGER.info(String.format("%s %s credited from %s.", amount, currency,
                sourceAccount));
        targetAccount.setBalance(BigDecimal.valueOf(targetAccount.getBalance().doubleValue()
                + amount.doubleValue()));
        LOGGER.info(String.format("%s %s debited to %s.", amount, currency,
                targetAccount));

        return new TransactionResult(id, new Account(sourceAccount.getAccountNumber(), sourceAccount.getBalance(),
                sourceAccount.getCurrency(), sourceAccount.getCreatedAt()),
                new Account(targetAccount.getAccountNumber(), targetAccount.getBalance(),
                        targetAccount.getCurrency(), targetAccount.getCreatedAt()));

    }
}
