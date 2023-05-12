package yan0kom.userbal.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserFull implements Serializable {
    static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String password;
    private UserAccount account;
    private List<UserEmail> emails;
    private List<UserPhone> phones;
}
