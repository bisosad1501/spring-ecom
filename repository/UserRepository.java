package bisosad.kt2.repository;

import bisosad.kt2.model.Account;
import bisosad.kt2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount(Account account);

}
