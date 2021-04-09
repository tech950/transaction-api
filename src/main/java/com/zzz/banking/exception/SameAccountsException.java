package com.zzz.banking.exception;

import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

public class SameAccountsException extends TransactionRuntimeException {
    public SameAccountsException(@NotNull Long sourceAccountId, @NotNull Long targetAccountId) {
        super(String.format("Source Account %s and Target Account %s are same.",
                sourceAccountId, targetAccountId), HttpStatus.BAD_REQUEST);
    }
}
