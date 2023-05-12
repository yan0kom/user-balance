package yan0kom.userbal.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class PhoneCreateInDto {
    @NotNull
    @Size(min = 11, max = 13)
    private String phone;
}
