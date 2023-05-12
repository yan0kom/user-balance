package yan0kom.userbal.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yan0kom.userbal.dao.entity.Account;
import yan0kom.userbal.domain.entity.UserAccount;
import yan0kom.userbal.domain.exception.EntityNotFoundException;
import yan0kom.userbal.domain.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final Integer balanceInc;
    private final Integer balanceMaxValue;
    private final CacheManager cacheManager;

    public AccountServiceImpl(AccountDao accountDao,
                              @Value("${application.balance-increment:0}") Integer balanceInc,
                              @Value("${application.balance-max-value:100}") Integer balanceMaxValue,
                              CacheManager cacheManager) {
        this.accountDao = accountDao;
        this.balanceInc = balanceInc;
        this.balanceMaxValue = balanceMaxValue;
        this.cacheManager = cacheManager;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "userCache", allEntries = true)
    public void incrementBalance() {
        accountDao.incrementBalance(balanceInc, balanceMaxValue);
    }

    @Override
    @Transactional
    public BigDecimal transfer(UserAccount payerAccount, UserAccount payeeAccount, BigDecimal value) {
        var fromAcc = accountDao.findById(payerAccount.getId())
                .orElseThrow(() -> new EntityNotFoundException(Account.class, payerAccount.getId()));
        var toAcc = accountDao.findById(payeeAccount.getId())
                .orElseThrow(() -> new EntityNotFoundException(Account.class, payeeAccount.getId()));

        if (fromAcc.getBalance().compareTo(value) < 0) {
            throw new IllegalStateException("There is no enough balance");
        }

        fromAcc.setBalance(fromAcc.getBalance().subtract(value));
        toAcc.setBalance(toAcc.getBalance().add(value));
        fromAcc = accountDao.saveAllAndFlush(List.of(fromAcc, toAcc)).get(0);

        cacheManager.getCache("userCache").evict(payerAccount.getUserId());
        cacheManager.getCache("userCache").evict(payeeAccount.getUserId());

        return fromAcc.getBalance();
    }
}
