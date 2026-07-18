package com.vbank.transaction.scheduler;

import com.vbank.transaction.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyInterestScheduler {

    private final InterestService interestService;

    @Scheduled(cron = "0 * * * * *")
    public void processDailyInterest() {
        interestService.processDailyInterest();
    }
}
