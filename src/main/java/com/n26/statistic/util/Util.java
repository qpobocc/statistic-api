package com.n26.statistic.util;

import com.n26.statistic.model.Transaction;

import java.time.Instant;

public class Util {

    private static final long MINUTE = 60 * 1000;

    /**
     * Static method to check if transaction is valid for processing
     *
     * @param transaction to be validated
     * @return {@code true} if the transaction timestamp is less than 60 seconds ago and before now
     */
    public static boolean isValid(Transaction transaction) {
        long now = Instant.now().toEpochMilli();
        long interval = now - transaction.getTimestamp();
        return interval < MINUTE && interval >= 0;
    }

}
