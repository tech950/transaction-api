package com.zzz.banking.transaction;

import com.zzz.banking.transaction.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResult transferAmount(@RequestBody Transaction transaction) {
        return transactionService.transferAmount(transaction);
    }
}
