package com.n26.statistic.service.Impl;

import com.n26.statistic.model.Statistic;
import com.n26.statistic.model.Transaction;
import com.n26.statistic.service.StatisticService;
import com.n26.statistic.util.Operation;
import com.n26.statistic.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
@EnableScheduling
public class CustomStatisticService implements StatisticService {

    private static final Logger logger = LoggerFactory.getLogger(CustomStatisticService.class);

    /**
     * Priority queue to store transaction in ascending mode by timestamp
     */
    private PriorityQueue<Transaction> transactionQueue;

    /**
     * Sorted map to store transaction amounts in ascending mode.
     * Integer Value from the map is the amount's counter, number of amount's duplicates
     */
    private SortedMap<Double, Integer> sortedAmounts;

    private static Statistic actualStatistic;

    public CustomStatisticService() {
        this.transactionQueue = new PriorityQueue<>(Comparator.comparing(Transaction::getTimestamp));
        this.sortedAmounts = new TreeMap<>();
        this.actualStatistic = new Statistic();
    }

    /**
     * Synchronized Access to statistic
     */
    @Override
    public synchronized Statistic getStatistic() {
        logger.debug("Actual " + actualStatistic);
        return actualStatistic;
    }

    /**
     * Synchronized operation ADD
     */
    @Override
    public synchronized void processTransaction(Transaction transaction) {
        transactionQueue.add(transaction);
        calculateStatistic(transaction, Operation.ADD);
        logger.debug("Was added a new " + transaction);
    }

    private void calculateStatistic(Transaction transaction, Operation operation) {
        actualStatistic.setCount(transactionQueue.size());
        switch (operation) {
            case ADD: {
                actualStatistic.setSum(actualStatistic.getSum() + transaction.getAmount());
                actualStatistic.setAvg(actualStatistic.getSum() / actualStatistic.getCount());

                /** If it's a duplicated amount -> increase counter, else insert new */
                if (sortedAmounts.containsKey(transaction.getAmount())) {
                    int num = sortedAmounts.get(transaction.getAmount());
                    sortedAmounts.put(transaction.getAmount(), ++num);
                } else {
                    sortedAmounts.put(transaction.getAmount(), 1);
                }
                break;
            }
            case REMOVE: {
                actualStatistic.setSum(actualStatistic.getSum() - transaction.getAmount());
                actualStatistic.setAvg(actualStatistic.getCount() > 0 ? actualStatistic.getSum() / actualStatistic.getCount() : 0);

                /** If exist several such amounts - decrease counter, else remove */
                if (sortedAmounts.containsKey(transaction.getAmount()) && sortedAmounts.get(transaction.getAmount()) > 1) {
                    int num = sortedAmounts.get(transaction.getAmount());
                    sortedAmounts.put(transaction.getAmount(), --num);
                } else {
                    sortedAmounts.remove(transaction.getAmount());
                }
                break;
            }
        }
        actualStatistic.setMin(sortedAmounts.size() > 0 ? sortedAmounts.firstKey() : 0);
        actualStatistic.setMax(sortedAmounts.size() > 0 ? sortedAmounts.lastKey() : 0);
    }

    /**
     * Synchronized operation REMOVE
     */
    @Scheduled(cron = "* * * * * ?")
    private synchronized void cleanUpQueue() {
        Transaction transaction = transactionQueue.peek();
        while (transaction != null && !Util.isValid(transaction)) {
            transactionQueue.poll();
            calculateStatistic(transaction, Operation.REMOVE);
            logger.debug("Was removed a " + transaction);
            transaction = transactionQueue.peek();
        }
    }
}
