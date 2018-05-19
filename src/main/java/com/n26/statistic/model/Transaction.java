package com.n26.statistic.model;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Transaction {

    @NotNull
    private Double amount;
    @NotNull
    private Long timestamp;

    public Transaction() {
    }

    public Transaction(@NotNull Double amount, @NotNull Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    /**
     * @return the transaction amount
     */
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return the transaction time in epoch in millis in UTC time zone
     */
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, timestamp);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
