package bisosad.kt2.service;

import bisosad.kt2.model.Product;
import bisosad.kt2.model.Review;
import bisosad.kt2.repository.ProductRepository;
import bisosad.kt2.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public List<Product> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrue(); // Lấy sản phẩm nổi bật
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchByKeyword(keyword); // Tìm kiếm sản phẩm
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStockCount(updatedProduct.getStockCount());
        product.setFeatured(updatedProduct.isFeatured()); // Sử dụng đúng getter isFeatured
        return productRepository.save(product);
    }

    public void addReview(Long productId, int rating, String comment) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm!"));
        Review review = new Review(product, rating, comment);
        reviewRepository.save(review);
    }
    public Page<Product> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }
    public List<Product> getAllProductsNoPaging() {
        return productRepository.findAll(); // Lấy tất cả sản phẩm không phân trang
    }
    public void saveProduct(Product product) {
        productRepository.save(product);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
