package bisosad.kt2.service;

import bisosad.kt2.model.Category;
import bisosad.kt2.model.Product;
import bisosad.kt2.repository.CategoryRepository;
import bisosad.kt2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Product> getProductsByCategoryId(int id) {
        return productRepository.findByCategoryId(id);
    }
    public List<Category> getRootCategories() {
        return categoryRepository.findByParentCategoryIdIsNull();
    }

    // Lấy danh mục con dựa trên parent ID
    public List<Category> getSubCategories(int parentCategoryId) {
        return categoryRepository.findByParentCategoryId(parentCategoryId);
    }

    public List<Category> getAllRootCategories() {
        return categoryRepository.findByParentCategoryIsNull();
    }
}


