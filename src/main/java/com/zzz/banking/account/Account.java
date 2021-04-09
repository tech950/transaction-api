package com.zzz.banking.account;

import java.math.BigDecimal;
import java.util.Date;

public class Account {

    private Long accountNumber;
    private BigDecimal balance;
    private String currency;
    private Date createdAt;

    public Account() {
    }

    public Account(Long accountNumber, BigDecimal balance, String currency, Date createdAt) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
