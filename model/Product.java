package bisosad.kt2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int stockCount;

    @ManyToOne
    @JoinColumn(name = "categoryid", referencedColumnName = "id")
    private Category category;

    @Column(name = "image")
    private String imageUrl; // Đường dẫn tới ảnh sản phẩm

    @Column(name = "is_featured", nullable = false)
    private boolean isFeatured; // Đánh dấu sản phẩm nổi bật

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
