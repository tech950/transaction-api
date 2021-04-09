package com.zzz.banking.exception;

import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

public class NoAccountFoundException extends TransactionRuntimeException {
    public NoAccountFoundException(@NotNull Long sourceAccountId, @NotNull Long targetAccountId) {
        super(String.format("Source Account %s or Target Account %s does not exist.",
                sourceAccountId, targetAccountId), HttpStatus.NOT_FOUND);
    }
}
