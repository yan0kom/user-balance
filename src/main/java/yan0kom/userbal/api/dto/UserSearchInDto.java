package yan0kom.userbal.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class UserSearchInDto {
    @Past
    private LocalDate dateOfBirthAfter;
    @Size(min = 11, max = 13)
    private String phone;
    private String namePrefix;
    @Email
    private String email;
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;

    @AssertTrue(message = "{UserSearchInDto.isPhone.message}" )
    private boolean isPhone() {
        return phone == null || ObjectUtils.allNull(dateOfBirthAfter, namePrefix, email);
    }

    @AssertTrue(message = "{UserSearchInDto.isEmail.message}")
    private boolean isEmail() {
        return email == null || ObjectUtils.allNull(dateOfBirthAfter, namePrefix, phone);
    }

}
