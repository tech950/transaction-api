package com.zzz.banking.transaction.dataaccessobject;

import com.zzz.banking.transaction.dataobject.TransactionDO;
import org.springframework.data.repository.CrudRepository;

public interface ITransactionCollection extends CrudRepository<TransactionDO, Long> {
    TransactionDO save(TransactionDO transaction);
}
