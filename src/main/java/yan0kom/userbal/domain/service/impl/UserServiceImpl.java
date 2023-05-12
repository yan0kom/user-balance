package yan0kom.userbal.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yan0kom.userbal.domain.UserFilter;
import yan0kom.userbal.domain.entity.UserEmail;
import yan0kom.userbal.domain.entity.UserFull;
import yan0kom.userbal.domain.entity.UserHead;
import yan0kom.userbal.domain.entity.UserPhone;
import yan0kom.userbal.domain.exception.EntityNotFoundException;
import yan0kom.userbal.domain.repo.UserRepo;
import yan0kom.userbal.domain.service.AccountService;
import yan0kom.userbal.domain.service.UserService;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final AccountService accountService;

    @Override
    @Cacheable(cacheNames = "userCache", key = "#id")
    public Optional<UserFull> getById(Long id) {
        return userRepo.get(id);
    }

    @Override
    public Page<UserHead> search(UserFilter filter, Pageable pageable) {
        return userRepo.find(filter, pageable);
    }

    @Override
    @CacheEvict(cacheNames = "userCache", key = "#result.userId")
    public UserEmail addEmail(String email) {
        return userRepo.addEmail(authUserId(), email);
    }

    @Override
    @CacheEvict(cacheNames = "userCache", key = "#result.userId")
    public UserEmail updateEmail(Long id, String email) {
        return userRepo.updateEmail(id, authUserId(), email);
    }

    @Override
    @CacheEvict(cacheNames = "userCache", key = "target.authUserId()")
    public void deleteEmail(Long id) {
        userRepo.deleteEmail(id, authUserId());
    }

    @Override
    @CacheEvict(cacheNames = "userCache", key = "#result.userId")
    public UserPhone addPhone(String phone) {
        return userRepo.addPhone(authUserId(), phone);
    }

    @Override
    @CacheEvict(cacheNames = "userCache", key = "#result.userId")
    public UserPhone updatePhone(Long id, String phone) {
        return userRepo.updatePhone(id, authUserId(), phone);
    }

    @Override
    @CacheEvict(cacheNames = "userCache", key = "target.authUserId()")
    public void deletePhone(Long id) {
        userRepo.deletePhone(id, authUserId());
    }

    @Override
    public BigDecimal transfer(Long payeeId, BigDecimal value) {
        var payer = getById(authUserId()).orElseThrow(() -> new EntityNotFoundException(UserFull.class, authUserId()));
        var payee = getById(payeeId).orElseThrow(() -> new EntityNotFoundException(UserFull.class, payeeId));

        return accountService.transfer(payer.getAccount(), payee.getAccount(), value);
    }

    public Long authUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
