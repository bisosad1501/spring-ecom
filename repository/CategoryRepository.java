package bisosad.kt2.repository;

import bisosad.kt2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByParentCategoryId(Integer parentCategoryId);
    List<Category> findByParentCategoryIdIsNull(); // Truy vấn danh mục gốc

    List<Category> findByParentCategoryIsNull();
}

