package yan0kom.userbal.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class AccountWorker {
    private final AccountService accountService;

    @Scheduled(cron = "${application.balance-increment-cron:-}")
    public void incrementBalance() {
        log.debug("Start scheduled balance increment");
        accountService.incrementBalance();
        log.debug("Scheduled balance increment completed");
    }
}
