package yan0kom.userbal.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class UserPhone implements Serializable {
    static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String phone;
}
