package yan0kom.userbal.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserHead {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String password;
}
