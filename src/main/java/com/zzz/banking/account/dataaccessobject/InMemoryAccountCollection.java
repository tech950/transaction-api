package com.zzz.banking.account.dataaccessobject;

import com.zzz.banking.account.Account;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In memory class to store the Accounts
 */
@Repository
public class InMemoryAccountCollection implements IAccountCollection {

    private List<Account> accounts = new ArrayList<>();

    public InMemoryAccountCollection() {
        try {
            accounts.add(createAccount(Long.valueOf(4444), BigDecimal.valueOf(6000.00), "GBP"));
            accounts.add(createAccount(Long.valueOf(5555), BigDecimal.valueOf(3000.00), "GBP"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Account createAccount(Long accountNumber, BigDecimal balance, String currency) throws ParseException {
        return new Account(accountNumber, balance, currency,
                new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-09"));
    }

    public Optional<Account> getAccount(Long accountNumber) {
        return accounts.stream().filter(account -> account.getAccountNumber().equals(accountNumber)).findFirst();
    }


}
