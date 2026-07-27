package com.vbank.transaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InterestService {

    public void processDailyInterest() {
        log.info("Running daily interest job...");
    }

}
