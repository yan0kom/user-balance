package yan0kom.userbal.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yan0kom.userbal.domain.entity.UserPhone;

@NoArgsConstructor
@Getter
@Setter
public class PhoneOutDto {
    private Long id;
    private Long userId;
    private String phone;

    public static PhoneOutDto from(UserPhone userPhone) {
        var dto = new PhoneOutDto();
        dto.setId(userPhone.getId());
        dto.setUserId(userPhone.getUserId());
        dto.setPhone(userPhone.getPhone());
        return dto;
    }
}
