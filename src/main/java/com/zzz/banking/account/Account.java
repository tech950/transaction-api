package com.zzz.banking.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class Account {

    private Long accountNumber;
    private BigDecimal balance;
    private String currency;
    private Date createdAt;

}
