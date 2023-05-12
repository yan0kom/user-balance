package yan0kom.userbal.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yan0kom.userbal.domain.UserFilter;
import yan0kom.userbal.domain.entity.UserEmail;
import yan0kom.userbal.domain.entity.UserFull;
import yan0kom.userbal.domain.entity.UserHead;
import yan0kom.userbal.domain.entity.UserPhone;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {
    Optional<UserFull> getById(Long id);
    Page<UserHead> search(UserFilter filter, Pageable pageable);

    UserEmail addEmail(String email);
    UserEmail updateEmail(Long id, String email);
    void deleteEmail(Long id);

    UserPhone addPhone(String phone);
    UserPhone updatePhone(Long id, String phone);
    void deletePhone(Long id);

    BigDecimal transfer(Long payeeId, BigDecimal value);
}
