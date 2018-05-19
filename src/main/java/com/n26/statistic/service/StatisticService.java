package com.n26.statistic.service;

import com.n26.statistic.model.Statistic;
import com.n26.statistic.model.Transaction;

/**
 * Interface for processing transactions statistic
 */
public interface StatisticService {

    /**
     * Include given transaction to watchlist
     *
     * @param transaction to be processed
     */
    void processTransaction(Transaction transaction);

    /**
     * Returns the statistic based of the transactions of the last 60 seconds
     *
     * @return actual Statistic
     */
    Statistic getStatistic();


}
