package bisosad.kt2.repository;

import bisosad.kt2.model.Cart;
import bisosad.kt2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndStatus(Long userId, String status);
    Optional<Cart> findByUser(User user);
    Optional<Cart> findByUserId(Long userId);
}
