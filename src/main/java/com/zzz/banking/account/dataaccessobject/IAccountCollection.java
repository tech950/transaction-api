package com.zzz.banking.account.dataaccessobject;

import com.zzz.banking.account.dataobject.AccountDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IAccountCollection extends CrudRepository<AccountDO, Long> {
    Optional<AccountDO> findByAccountNumber(Long accountNumber);
}
