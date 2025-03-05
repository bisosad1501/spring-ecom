package bisosad.kt2.controller;

import bisosad.kt2.model.Account;
import bisosad.kt2.model.Cart;
import bisosad.kt2.model.CartItem;
import bisosad.kt2.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add/{productId}")
    public String addToCart(@ModelAttribute("loggedInUser") Account loggedInUser, @PathVariable Long productId, @RequestParam int quantity) {
        if (loggedInUser == null) {
            return "redirect:/accounts/login"; // Chuyển hướng đến trang login nếu chưa đăng nhập
        }

        cartService.addToCart(loggedInUser.getId(), productId, quantity);
        return "redirect:/carts/view";
    }

    // Hiển thị giỏ hàng
    @GetMapping("/view")
    public String viewCart(@ModelAttribute("loggedInUser") Account loggedInUser, Model model) {
        if (loggedInUser == null) {
            return "redirect:/accounts/login"; // Chuyển hướng nếu chưa đăng nhập
        }

        // Lấy giỏ hàng từ service (đảm bảo luôn có giỏ hàng)
        Cart cart = cartService.getOrCreateCart(loggedInUser.getId());
        model.addAttribute("cart", cart);

        return "carts/carts";
    }


    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @PostMapping("/update/{cartItemId}")
    public String updateCartItem(@ModelAttribute("loggedInUser") Account loggedInUser,
                                 @PathVariable Long cartItemId, @RequestParam int quantity) {
        if (loggedInUser == null) {
            return "redirect:/accounts/login"; // Chuyển hướng nếu chưa đăng nhập
        }

        cartService.updateCartItem(loggedInUser.getId(), cartItemId, quantity);
        return "redirect:/carts/view";
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @PostMapping("/remove/{cartItemId}")
    public String removeCartItem(@ModelAttribute("loggedInUser") Account loggedInUser,
                                 @PathVariable Long cartItemId) {
        if (loggedInUser == null) {
            return "redirect:/accounts/login"; // Chuyển hướng nếu chưa đăng nhập
        }

        cartService.removeCartItem(loggedInUser.getId(), cartItemId);
        return "redirect:/carts/view";
    }

}