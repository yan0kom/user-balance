package yan0kom.userbal;

import yan0kom.userbal.dao.entity.Account;

import java.math.BigDecimal;

public class AccountFactory {
    public static Account withIdAndUserId(Long id, Long userId) {
        return new Account(id, userId, BigDecimal.valueOf(100.00), BigDecimal.valueOf(100.00));
    }
}
