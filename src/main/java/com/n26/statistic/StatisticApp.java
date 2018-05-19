package com.n26.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.n26.statistic")
@EnableAutoConfiguration
public class StatisticApp {

    public static void main(String[] args) {
        SpringApplication.run(StatisticApp.class, args);
    }
}
