package com.zzz.banking.transaction.service;

import com.zzz.banking.account.Account;
import com.zzz.banking.account.dataaccessobject.IAccountCollection;
import com.zzz.banking.exception.InSufficientBalanceException;
import com.zzz.banking.exception.NoAccountFoundException;
import com.zzz.banking.exception.SameAccountsException;
import com.zzz.banking.transaction.Transaction;
import com.zzz.banking.transaction.TransactionResult;
import com.zzz.banking.transaction.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    IAccountCollection accountCollection;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

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
        Optional<Account> sourceAccount = accountCollection.getAccount(transaction.getSourceAccountId());
        Optional<Account> targetAccount = accountCollection.getAccount(transaction.getTargetAccountId());
        if (!sourceAccount.isPresent() || !targetAccount.isPresent()) {
            throw new NoAccountFoundException(transaction.getSourceAccountId(),
                    transaction.getTargetAccountId());
        } else if (TransactionUtils.isSourceAndTargetAccountSame(transaction)) {
            throw new SameAccountsException(transaction.getSourceAccountId(),
                    transaction.getTargetAccountId());
        } else if (!TransactionUtils.isSufficientBalanceForTransfer(sourceAccount, transaction.getAmount())) {
            throw new InSufficientBalanceException(transaction.getSourceAccountId());
        } else {
            return TransactionUtils.transferMoney(sourceAccount, targetAccount,
                    transaction.getAmount(), transaction.getCurrency());

        }
    }
}
