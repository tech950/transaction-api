package com.zzz.banking.account.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNT")
public class AccountDO {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountNumber;
    private BigDecimal balance;
    private String currency;
    private Date createdAt;

    public AccountDO(Long accountNumber, BigDecimal balance, String currency, Date createdAt) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }
}
