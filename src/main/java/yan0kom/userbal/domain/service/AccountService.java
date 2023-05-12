package yan0kom.userbal.domain.service;

import yan0kom.userbal.domain.entity.UserAccount;

import java.math.BigDecimal;

public interface AccountService {
    void incrementBalance();

    BigDecimal transfer(UserAccount payerAccount, UserAccount payeeAccount, BigDecimal value);
}
