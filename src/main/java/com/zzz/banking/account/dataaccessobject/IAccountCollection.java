package com.zzz.banking.account.dataaccessobject;

import com.zzz.banking.account.Account;

import java.util.Optional;

public interface IAccountCollection {
    Optional<Account> getAccount(Long id);
}
