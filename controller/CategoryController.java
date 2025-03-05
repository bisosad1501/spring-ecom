package bisosad.kt2.controller;

import bisosad.kt2.model.Category;
import bisosad.kt2.model.Product;
import bisosad.kt2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller


@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String viewCategories(Model model) {
        List<Category> rootCategories = categoryService.getRootCategories(); // Lấy danh mục gốc
        for (Category category : rootCategories) {
            List<Category> subCategories = categoryService.getSubCategories(Math.toIntExact(category.getId()));
            category.setSubCategories(subCategories); // Gán danh mục con vào mỗi danh mục gốc
        }
        model.addAttribute("rootCategories", rootCategories); // Truyền dữ liệu danh mục gốc vào model
        return "categories/categories"; // Tên file HTML
    }

    @GetMapping("/{id}")
    public String viewCategoryProducts(@PathVariable("id") int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        List<Product> products = categoryService.getProductsByCategoryId(id);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "category-products"; // HTML page for category products
    }
}
