package yan0kom.userbal.dao;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yan0kom.userbal.dao.entity.EmailData;
import yan0kom.userbal.dao.entity.PhoneData;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final EmailDataDao emailDataDao;
    private final PhoneDataDao phoneDataDao;
    private final UserDao userDao;

    public UserDetailsServiceImpl(EmailDataDao emailDataDao, PhoneDataDao phoneDataDao, UserDao userDao) {
        this.emailDataDao = emailDataDao;
        this.phoneDataDao = phoneDataDao;
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userId = phoneDataDao.findByPhone(username).map(PhoneData::getUserId)
                .or(() -> emailDataDao.findByEmail(username).map(EmailData::getUserId))
                .orElseThrow(() -> new UsernameNotFoundException(username));

        var user = userDao.findById(userId).orElseThrow(() -> new UsernameNotFoundException(username));

        return User.withUsername(userId.toString())
                .password(user.getPassword())
                .authorities("api.user")
                .build();
    }
}
