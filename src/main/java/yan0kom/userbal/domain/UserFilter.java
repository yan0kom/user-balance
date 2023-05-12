package yan0kom.userbal.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class UserFilter {
    private LocalDate dateOfBirthAfter;
    private String phone;
    private String namePrefix;
    private String email;
}
