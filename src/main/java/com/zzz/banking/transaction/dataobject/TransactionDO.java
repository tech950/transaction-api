package com.zzz.banking.transaction.dataobject;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

public class TransactionDO {

    @Id
    @GeneratedValue
    private Long id;
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
    private String currency;

    public TransactionDO(Long sourceAccountId, Long targetAccountId, BigDecimal amount, String currency) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
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
        return "TransactionDO{" +
                "id=" + id +
                ", sourceAccountId=" + sourceAccountId +
                ", targetAccountId=" + targetAccountId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
