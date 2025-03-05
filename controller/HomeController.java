package bisosad.kt2.controller;

import bisosad.kt2.model.Category;
import bisosad.kt2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller

    @RequestMapping("/")
    public class HomeController {

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
            return "/index"; // Tên file HTML
        }

}
