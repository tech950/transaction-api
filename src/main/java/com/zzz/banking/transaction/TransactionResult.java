package com.zzz.banking.transaction;

import com.zzz.banking.account.Account;

public class TransactionResult {

    private Long id;
    private Account sourceAccount;
    private Account targetAccount;

    public TransactionResult() {
    }

    public TransactionResult(Long id, Account sourceAccount, Account targetAccount) {
        this.id = id;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

    public Long getId() {
        return id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }
}
