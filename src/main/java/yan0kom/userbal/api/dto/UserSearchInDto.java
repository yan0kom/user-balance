package yan0kom.userbal.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class UserSearchInDto {
    private LocalDate dateOfBirthAfter;
    //@Size(min = 11, max = 13)
    private String phone;
    private String namePrefix;
    //@Email
    private String email;
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
}
