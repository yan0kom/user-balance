package yan0kom.userbal.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class TransferOutDto {
    private BigDecimal balance;

    public static TransferOutDto from(BigDecimal balance) {
        var dto = new TransferOutDto();
        dto.setBalance(balance);
        return dto;
    }
}
