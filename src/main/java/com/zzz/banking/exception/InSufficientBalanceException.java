package com.zzz.banking.exception;

import org.springframework.http.HttpStatus;

public class InSufficientBalanceException extends TransactionRuntimeException {

    public InSufficientBalanceException(Long sourceAccountId) {
        super(String.format("Source Account %s do not have sufficient balance for this transaction.",
                sourceAccountId), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
    }
}
