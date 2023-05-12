package yan0kom.userbal.domain.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import yan0kom.userbal.AccountFactory;
import yan0kom.userbal.UserAccountFactory;
import yan0kom.userbal.dao.AccountDao;
import yan0kom.userbal.dao.AccountServiceImpl;
import yan0kom.userbal.dao.entity.Account;
import yan0kom.userbal.domain.entity.UserAccount;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(Lifecycle.PER_CLASS)
class AccountService_TransferTest {
    AccountDao accountDao;
    AccountService accountService;

    Account acc1 = AccountFactory.withIdAndUserId(1L, 1L);
    Account acc2 = AccountFactory.withIdAndUserId(2L, 2L);
    UserAccount user1acc = UserAccountFactory.withIdAndUserId(1L, 1L);
    UserAccount user2acc = UserAccountFactory.withIdAndUserId(2L, 2L);

    @BeforeAll
    void setup() {
        accountDao = mock(AccountDao.class);
        when(accountDao.findById(1L)).thenReturn(Optional.of(acc1));
        when(accountDao.findById(2L)).thenReturn(Optional.of(acc2));
        when(accountDao.saveAllAndFlush(anyIterable()))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        var cacheManager = mock(CacheManager.class);
        when(cacheManager.getCache("userCache")).thenReturn(mock(Cache.class));

        accountService = new AccountServiceImpl(accountDao, 0, 100, cacheManager);
    }

    @Test
    void transfer() {
        assertThatThrownBy(() -> accountService.transfer(user1acc, user2acc, BigDecimal.valueOf(500)))
                .isInstanceOf(IllegalStateException.class);

        var balance = accountService.transfer(user1acc, user2acc, BigDecimal.valueOf(30));

        assertEquals(BigDecimal.valueOf(70.00), balance);
        assertEquals(BigDecimal.valueOf(70.00), acc1.getBalance());
        assertEquals(BigDecimal.valueOf(130.00), acc2.getBalance());
    }
}
