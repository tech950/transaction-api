package com.zzz.banking.exception;

import org.springframework.http.HttpStatus;

public class TransactionRuntimeException extends RuntimeException {

    private HttpStatus status;

    public TransactionRuntimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
