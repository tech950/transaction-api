package com.zzz.banking.transaction;

import com.zzz.banking.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Util class for Transaction
 */
public class TransactionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionUtils.class);

    private TransactionUtils() {
    }

    /**
     * Returns true if the Source account contains sufficient balance for transfer
     *
     * @return
     */
    public static boolean isSufficientBalanceForTransfer(Optional<Account> sourceAccount,
                                                         @NotNull BigDecimal amount) {
        return sourceAccount.isPresent()
                && sourceAccount.get().getBalance().doubleValue() >= amount.doubleValue();
    }

    /**
     * Returns true if Source and Target Accounts are same
     *
     * @param transaction
     * @return
     */
    public static boolean isSourceAndTargetAccountSame(Transaction transaction) {
        return transaction.getSourceAccountId().equals(transaction.getTargetAccountId());
    }

    /**
     * Returns true if both Source and Target accounts exist
     *
     * @return
     */
    public static boolean isExistingAccounts(Optional<Account> sourceAccount,
                                             Optional<Account> targetAccount) {
        return sourceAccount.isPresent() &&
                targetAccount.isPresent();
    }

    /**
     * Transfer the specified amount from Source Account to Target Account and returns the result
     */
    public static TransactionResult transferMoney(Optional<Account> sourceAccount,
                                                  Optional<Account> targetAccount,
                                                  @NotNull BigDecimal amount,
                                                  @NotNull String currency) {
        if (sourceAccount.isPresent() && targetAccount.isPresent()) {
            if (amount.doubleValue() > 0) {
                sourceAccount.get()
                        .setBalance(BigDecimal.valueOf(sourceAccount.get().getBalance().doubleValue()
                                - amount.doubleValue()));
                LOGGER.info(String.format("%s %s credited from %s.", amount, currency,
                        sourceAccount));
                targetAccount.get().setBalance(BigDecimal.valueOf(targetAccount.get().getBalance().doubleValue()
                        + amount.doubleValue()));
                LOGGER.info(String.format("%s %s debited to %s.", amount, currency,
                        targetAccount));
            }
            return new TransactionResult(Long.valueOf(0), sourceAccount.get(), targetAccount.get());
        }
        return null;
    }

}
