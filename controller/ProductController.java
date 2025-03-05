package bisosad.kt2.controller;

import bisosad.kt2.model.Account;
import bisosad.kt2.model.Product;
import bisosad.kt2.service.CartService;
import bisosad.kt2.service.CategoryService;
import bisosad.kt2.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    // Hiển thị danh sách sản phẩm với phân trang
    @GetMapping
    public String getAllProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "15") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getAllProducts(pageable);

        model.addAttribute("products", productPage.getContent()); // Danh sách sản phẩm hiện tại
        model.addAttribute("currentPage", page); // Trang hiện tại
        model.addAttribute("totalPages", productPage.getTotalPages()); // Tổng số trang

        return "products/list"; // Trang HTML hiển thị danh sách sản phẩm
    }

    // Tìm kiếm sản phẩm với phân trang
    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "15") int size,
                                 Model model) {
        Page<Product> productPage = productService.searchProducts(keyword, page, size);

        // Kiểm tra số trang và xử lý trường hợp không có sản phẩm
        int totalPages = productPage.getTotalPages();
        if (totalPages == 0) {
            totalPages = 1; // Đặt mặc định 1 trang để tránh lỗi Thymeleaf
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);

        return "products/list";
    }


    // Chi tiết sản phẩm
    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/detail";
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/{id}/add-to-cart")
    public String addToCart(@PathVariable Long id, @RequestParam int quantity,
                            HttpSession session, Model model) {
        // Lấy đối tượng loggedInUser từ session
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            model.addAttribute("message", "Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng!");
            return "redirect:/accounts/login";
        }

        // Lấy username
        String username = loggedInUser.getUsername();
        System.out.println("Thêm sản phẩm vào giỏ hàng cho user: " + username);

        // Logic thêm sản phẩm vào giỏ hàng
        try {
            cartService.addToCart(loggedInUser.getId(), id, quantity);
            model.addAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
        }

        return "redirect:/products";
    }



    // Tạo sản phẩm mới
    @PostMapping
    public String createProduct(@ModelAttribute Product product) {
        productService.createProduct(product);
        logger.info("Tạo sản phẩm mới thành công: {}", product);
        return "redirect:/products";
    }

    // Cập nhật sản phẩm
    @PostMapping("/{id}/update")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productService.updateProduct(id, product);
        logger.info("Cập nhật sản phẩm thành công: id={}, product={}", id, product);
        return "redirect:/products";
    }

    // Xóa sản phẩm
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        logger.info("Xóa sản phẩm thành công: id={}", id);
        return "redirect:/products";
    }
}
