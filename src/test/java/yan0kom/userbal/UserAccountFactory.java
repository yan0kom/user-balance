package yan0kom.userbal;

import yan0kom.userbal.domain.entity.UserAccount;

import java.math.BigDecimal;

public class UserAccountFactory {
    public static UserAccount withIdAndUserId(Long id, Long userId) {
        return new UserAccount(id, userId, BigDecimal.valueOf(100.00), BigDecimal.valueOf(100.00));
    }
}
