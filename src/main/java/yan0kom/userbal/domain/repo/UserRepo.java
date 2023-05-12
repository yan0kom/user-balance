package yan0kom.userbal.domain.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yan0kom.userbal.domain.UserFilter;
import yan0kom.userbal.domain.entity.UserEmail;
import yan0kom.userbal.domain.entity.UserFull;
import yan0kom.userbal.domain.entity.UserHead;
import yan0kom.userbal.domain.entity.UserPhone;

import java.util.Optional;

public interface UserRepo {
    Optional<UserFull> get(Long id);
    Optional<UserFull> set(UserFull user);
    Page<UserHead> find(UserFilter filter, Pageable pageable);

    UserEmail addEmail(Long userId, String email);
    UserEmail updateEmail(Long id, Long userId, String email);
    void deleteEmail(Long id, Long userId);

    UserPhone addPhone(Long userId, String phone);
    UserPhone updatePhone(Long id, Long userId, String phone);
    void deletePhone(Long id, Long userId);
}
