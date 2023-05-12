package yan0kom.userbal.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class UserAccount implements Serializable {
    static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private BigDecimal deposit;
    private BigDecimal balance;
}
