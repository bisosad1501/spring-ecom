package bisosad.kt2.repository;

import bisosad.kt2.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Nếu cần, có thể thêm các phương thức query bổ sung tại đây
}
