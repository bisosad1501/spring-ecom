package bisosad.kt2.controller;

import bisosad.kt2.model.Category;
import bisosad.kt2.model.Product;
import bisosad.kt2.service.CategoryService;
import bisosad.kt2.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Trang quản lý sản phẩm
    @GetMapping
    public String adminDashboard(HttpSession session, Model model,
                                 @RequestParam(required = false, defaultValue = "") String keyword) {
        // Kiểm tra đăng nhập
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/accounts/login";
        }

        // Lấy danh sách sản phẩm
        List<Product> products;
        if (keyword.isEmpty()) {
            products = productService.getAllProductsNoPaging();
        } else {
            products = productService.searchProducts(keyword);
        }

        // Lấy danh sách danh mục
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("loggedInUser", loggedInUser);
        return "admin";
    }

    // Thêm sản phẩm mới
    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        // Lưu sản phẩm trực tiếp vào bảng Product
        productService.createProduct(product);

        // Chuyển hướng về trang quản lý
        return "redirect:/admin";
    }


    // Cập nhật sản phẩm
    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("categoryId") Long categoryId) {
        // Lấy danh mục theo ID
        Category category = categoryService.getCategoryById(categoryId.intValue());
        product.setCategory(category);

        // Cập nhật sản phẩm
        productService.updateProduct(product.getId(), product);
        return "redirect:/admin";
    }

    // Xóa sản phẩm
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/accounts/login";
        }

        // Xóa sản phẩm
        productService.deleteProduct(id);
        return "redirect:/admin";
    }
}
