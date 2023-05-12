package yan0kom.userbal.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class UserSearchOutDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
}
