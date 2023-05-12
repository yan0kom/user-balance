package yan0kom.userbal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yan0kom.userbal.dao.entity.EmailData;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface EmailDataDao extends JpaRepository<EmailData, Long> {
    Stream<EmailData> findByUserId(Long userId);
    Optional<EmailData> findByEmail(String email);

    Long countByUserId(Long userId);
}
