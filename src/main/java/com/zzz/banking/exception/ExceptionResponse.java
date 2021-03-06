package com.zzz.banking.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ExceptionResponse {

    private Date timestamp;

    private String message;

    private String details;

    private HttpStatus status;

    public ExceptionResponse(Date timestamp, String message, String details, HttpStatus status) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                ", status=" + status +
                '}';
    }

}

