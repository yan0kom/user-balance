package yan0kom.userbal.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yan0kom.userbal.domain.entity.UserFull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class UserGetFullOutDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private BigDecimal accountDeposit;
    private BigDecimal accountBalance;
    private List<EmailOutDto> emails;
    private List<PhoneOutDto> phones;

    public static UserGetFullOutDto from(UserFull userFull) {
        var dto = new UserGetFullOutDto();
        dto.setId(userFull.getId());
        dto.setName(userFull.getName());
        dto.setDateOfBirth(userFull.getDateOfBirth());
        dto.setAccountDeposit(userFull.getAccount().getDeposit());
        dto.setAccountBalance(userFull.getAccount().getBalance());
        dto.setEmails(userFull.getEmails().stream().map(EmailOutDto::from).collect(Collectors.toList()));
        dto.setPhones(userFull.getPhones().stream().map(PhoneOutDto::from).collect(Collectors.toList()));
        return dto;
    }
}
