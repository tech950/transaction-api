package com.zzz.banking.transaction.service;

import com.zzz.banking.transaction.Transaction;
import com.zzz.banking.transaction.TransactionResult;

public interface ITransactionService {

    TransactionResult transferAmount(Transaction transaction);
}
