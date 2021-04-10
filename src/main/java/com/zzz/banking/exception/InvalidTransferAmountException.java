package com.zzz.banking.exception;

import org.springframework.http.HttpStatus;

public class InvalidTransferAmountException extends TransactionRuntimeException{
    public InvalidTransferAmountException() {
        super("Transaction amount should be greater than zero", HttpStatus.BAD_REQUEST);
    }
}
