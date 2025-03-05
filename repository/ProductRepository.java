package bisosad.kt2.repository;

import bisosad.kt2.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsFeaturedTrue(); // Lấy các sản phẩm nổi bật

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> searchByKeyword(@Param("keyword") String keyword); // Tìm kiếm theo từ khóa

    List<Product> findByCategoryId(Integer categoryId);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

}
