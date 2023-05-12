package yan0kom.userbal.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yan0kom.userbal.domain.entity.UserEmail;

@NoArgsConstructor
@Getter
@Setter
public class EmailOutDto {
    private Long id;
    private Long userId;
    private String email;

    public static EmailOutDto from(UserEmail userEmail) {
        var dto = new EmailOutDto();
        dto.setId(userEmail.getId());
        dto.setUserId(userEmail.getUserId());
        dto.setEmail(userEmail.getEmail());
        return dto;
    }
}
