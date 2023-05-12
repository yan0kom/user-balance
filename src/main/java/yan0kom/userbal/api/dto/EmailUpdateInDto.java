package yan0kom.userbal.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class EmailUpdateInDto {
    @NotNull
    private Long id;
    @NotNull
    @Email
    private String email;
}
