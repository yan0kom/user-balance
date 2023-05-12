package yan0kom.userbal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yan0kom.userbal.dao.entity.PhoneData;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface PhoneDataDao extends JpaRepository<PhoneData, Long> {
    Stream<PhoneData> findByUserId(Long userId);
    Optional<PhoneData> findByPhone(String phone);

    Long countByUserId(Long userId);
}
