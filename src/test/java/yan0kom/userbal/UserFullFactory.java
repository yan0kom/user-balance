package yan0kom.userbal;

import yan0kom.userbal.domain.entity.UserEmail;
import yan0kom.userbal.domain.entity.UserFull;
import yan0kom.userbal.domain.entity.UserPhone;

import java.time.LocalDate;
import java.util.List;

public class UserFullFactory {
    public static UserFull withId(Long id) {
        return new UserFull(
                id,
                "user-"+id,
                LocalDate.of(1970, 4, 30),
                "",
                UserAccountFactory.withIdAndUserId(id, id),
                List.of(new UserEmail(id, id, "user"+id+"@test.com")),
                List.of(new UserPhone(id, id, "7999555110"+id)));
    }
}
