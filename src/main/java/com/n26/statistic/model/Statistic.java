package com.n26.statistic.model;

import java.util.Objects;

public class Statistic {

    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;

    public Statistic() {
    }

    public Statistic(double sum, double avg, double max, double min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    /**
     * @return The total sum of transaction value in the last 60 seconds
     */
    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    /**
     * @return the average amount of transaction value in the last 60 seconds
     */
    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    /**
     * @return the highest transaction value in the last 60 seconds
     */
    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    /**
     * @return the lowest transaction value in the last 60 seconds
     */
    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    /**
     * @return the total number of transactions happened in the last 60 seconds
     */
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return Double.compare(statistic.sum, sum) == 0 &&
                Double.compare(statistic.avg, avg) == 0 &&
                Double.compare(statistic.max, max) == 0 &&
                Double.compare(statistic.min, min) == 0 &&
                count == statistic.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, avg, max, min, count);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }
}
