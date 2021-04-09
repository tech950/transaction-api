package com.zzz.banking.transaction;

import java.math.BigDecimal;

public class Transaction {
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
    private String currency;

    public Transaction() {
    }

    public Transaction(Long sourceAccountId, Long targetAccountId, BigDecimal amount, String currency) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sourceAccountId=" + sourceAccountId +
                ", targetAccountId=" + targetAccountId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
