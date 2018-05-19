package com.n26.statistic.controller;

import com.n26.statistic.model.Statistic;
import com.n26.statistic.model.Transaction;
import com.n26.statistic.service.StatisticService;
import com.n26.statistic.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StatisticController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private StatisticService service;

    /**
     * Endpont for processing new transactions. If transaction timestamp is older than 60 sec or later than present time, it will not be processed
     *
     * @param transaction to be processed
     * @return Response with HttpStatus.CREATED/NO_CONTENT
     */
    @PostMapping(name = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processTransaction(@RequestBody @Valid Transaction transaction) {
        if (Util.isValid(transaction)) {
            service.processTransaction(transaction);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        logger.debug(transaction + " is older than 60 sec, it was rejected");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpont for accessing actual statistics in JSON format
     *
     * @return An JSON object that represent current statistics
     */
    @GetMapping(name = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Statistic> getStatistic() {
        return new ResponseEntity<>(service.getStatistic(), HttpStatus.OK);
    }

}
