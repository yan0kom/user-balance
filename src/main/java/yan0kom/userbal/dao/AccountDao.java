package yan0kom.userbal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yan0kom.userbal.dao.entity.Account;

import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(Long userId);

    @Modifying
    @Query(nativeQuery = true, value = "call increment_balance(:inc, :maxInc)")
    void incrementBalance(@Param("inc") Integer inc, @Param("maxInc") Integer maxInc);
}
