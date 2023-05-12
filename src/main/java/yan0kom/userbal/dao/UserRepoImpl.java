package yan0kom.userbal.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yan0kom.userbal.dao.entity.Account;
import yan0kom.userbal.dao.entity.EmailData;
import yan0kom.userbal.dao.entity.PhoneData;
import yan0kom.userbal.dao.entity.User;
import yan0kom.userbal.domain.UserFilter;
import yan0kom.userbal.domain.entity.*;
import yan0kom.userbal.domain.exception.EntityNotFoundException;
import yan0kom.userbal.domain.repo.UserRepo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class UserRepoImpl implements UserRepo {
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final EmailDataDao emailDataDao;
    private final PhoneDataDao phoneDataDao;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserFull> get(Long id) {
        try {
            User user = userDao.findById(id).orElseThrow();
            Account account = accountDao.findByUserId(id).orElseThrow();
            var emailData = emailDataDao.findByUserId(id);
            var phoneData = phoneDataDao.findByUserId(id);
            try (emailData; phoneData) {
                return Optional.of(new UserFull(
                        user.getId(), user.getName(), user.getDateOfBirth(), user.getPassword(),
                        new UserAccount(account.getId(), account.getUserId(), account.getDeposit(), account.getBalance()),
                        emailData.map(ed -> new UserEmail(ed.getId(), ed.getUserId(), ed.getEmail())).collect(Collectors.toList()),
                        phoneData.map(pd -> new UserPhone(pd.getId(), pd.getUserId(), pd.getPhone())).collect(Collectors.toList())));
            }
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserHead> find(UserFilter filter, Pageable pageable) {
        Function<User, UserHead> converter = user ->
                new UserHead(user.getId(), user.getName(), user.getDateOfBirth(), user.getPassword());

        if (filter.getPhone() != null) {
            var user = phoneDataDao.findByPhone(filter.getPhone())
                    .flatMap(phoneData -> userDao.findById(phoneData.getUserId()))
                    .map(converter).stream().collect(Collectors.toList());
            return new PageImpl<UserHead>(user, pageable, user.size());
        }
        if (filter.getEmail() != null) {
            var user = emailDataDao.findByEmail(filter.getEmail())
                    .flatMap(emailData -> userDao.findById(emailData.getUserId()))
                    .map(converter).stream().collect(Collectors.toList());
            return new PageImpl<UserHead>(user, pageable, user.size());
        }
        if (filter.getDateOfBirthAfter() != null || filter.getNamePrefix() != null) {
            return userDao.findAll(new UserSrecification(filter), pageable).map(converter);
        }

        return userDao.findAll(pageable).map(converter);
    }

    @Override
    public Optional<UserFull> set(UserFull user) {
        return Optional.empty();
    }
    @Override
    @Transactional
    public UserEmail addEmail(Long userId, String email) {
        var emailData = emailDataDao.save(new EmailData(null, userId, email));
        return new UserEmail(emailData.getId(), emailData.getUserId(), emailData.getEmail());
    }

    @Override
    @Transactional
    public UserEmail updateEmail(Long id, Long userId, String email) {
        var emailData = emailDataDao.findById(id).orElseThrow(() -> new EntityNotFoundException(UserEmail.class, id));
        if (Objects.equals(emailData.getUserId(), userId)) {
            emailData = emailDataDao.save(new EmailData(id, userId, email));
        } else {
            throw new AccessDeniedException("User can change only self email");
        }
        return new UserEmail(emailData.getId(), emailData.getUserId(), emailData.getEmail());
    }

    @Override
    @Transactional
    public void deleteEmail(Long id, Long userId) {
        if (emailDataDao.countByUserId(userId) < 2) {
            throw new AccessDeniedException("User can't delete last email");
        }
        var emailData = emailDataDao.findById(id).orElseThrow(() -> new EntityNotFoundException(UserEmail.class, id));
        if (Objects.equals(emailData.getUserId(), userId)) {
            emailDataDao.deleteById(id);
        } else {
            throw new AccessDeniedException("User can delete only self email");
        }
    }

    @Override
    public UserPhone addPhone(Long userId, String phone) {
        var phoneData = phoneDataDao.save(new PhoneData(null, userId, phone));
        return new UserPhone(phoneData.getId(), phoneData.getUserId(), phoneData.getPhone());
    }

    @Override
    public UserPhone updatePhone(Long id, Long userId, String phone) {
        var phoneData = phoneDataDao.findById(id).orElseThrow(() -> new EntityNotFoundException(PhoneData.class, id));
        if (Objects.equals(phoneData.getUserId(), userId)) {
            phoneData = phoneDataDao.save(new PhoneData(id, userId, phone));
        } else {
            throw new AccessDeniedException("User can change only self phone");
        }
        return new UserPhone(phoneData.getId(), phoneData.getUserId(), phoneData.getPhone());
    }

    @Override
    public void deletePhone(Long id, Long userId) {
        if (phoneDataDao.countByUserId(userId) < 2) {
            throw new AccessDeniedException("User can't delete last phone");
        }
        var phoneData = phoneDataDao.findById(id).orElseThrow(() -> new EntityNotFoundException(PhoneData.class, id));
        if (Objects.equals(phoneData.getUserId(), userId)) {
            phoneDataDao.deleteById(id);
        } else {
            throw new AccessDeniedException("User can delete only self phone");
        }
    }

    @RequiredArgsConstructor
    private static class UserSrecification implements Specification<User> {
        private final UserFilter filter;

        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            var predicates = new ArrayList<Predicate>(2);
            if (filter.getDateOfBirthAfter() != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("dateOfBirth"), filter.getDateOfBirthAfter()));
            }
            if (filter.getNamePrefix() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), filter.getNamePrefix() + '%'));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        }
    }
}
