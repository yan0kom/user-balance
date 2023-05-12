package yan0kom.userbal.domain.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import yan0kom.userbal.UserFullFactory;
import yan0kom.userbal.domain.entity.UserFull;
import yan0kom.userbal.domain.repo.UserRepo;
import yan0kom.userbal.domain.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@TestInstance(Lifecycle.PER_CLASS)
class UserServiceTest {
    AccountService accountService;
    UserService userService;

    UserFull user1 = UserFullFactory.withId(1L);
    UserFull user2 = UserFullFactory.withId(2L);

    @BeforeAll
    void setup() {
        var auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("1");
        SecurityContextHolder.getContext().setAuthentication(auth);

        var userRepo = mock(UserRepo.class);
        when(userRepo.get(1L)).thenReturn(Optional.of(user1));
        when(userRepo.get(2L)).thenReturn(Optional.of(user2));

        accountService = mock(AccountService.class);

        userService = new UserServiceImpl(userRepo, accountService);
    }

    @AfterAll
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getById() {
        assertTrue(userService.getById(1L).isPresent());
        assertEquals(1L, userService.getById(1L).get().getId());
        assertTrue(userService.getById(2L).isPresent());
        assertEquals(user2, userService.getById(2L).get());
    }

    @Test
    void search() {
    }

    @Test
    void addEmail() {
    }

    @Test
    void updateEmail() {
    }

    @Test
    void deleteEmail() {
    }

    @Test
    void addPhone() {
    }

    @Test
    void updatePhone() {
    }

    @Test
    void deletePhone() {
    }

    @Test
    void transfer() {
        when(accountService.transfer(any(), any(), eq(BigDecimal.TEN))).thenReturn(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, userService.transfer(2L, BigDecimal.TEN));

        verify(accountService, times(1))
                .transfer(user1.getAccount(), user2.getAccount(), BigDecimal.TEN);
    }
}
