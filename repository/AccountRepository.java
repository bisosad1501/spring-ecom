package bisosad.kt2.repository;

import bisosad.kt2.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsernameAndPassword(String username, String password);

    Account findByUsername(String username);
}
