package bisosad.kt2.controller;

import bisosad.kt2.model.Account;
import bisosad.kt2.model.Cart;
import bisosad.kt2.model.Order;
import bisosad.kt2.service.CartService;
import bisosad.kt2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCheckoutPage(@ModelAttribute("loggedInUser") Account loggedInUser, Model model) {
        // Kiểm tra nếu người dùng chưa đăng nhập
        if (loggedInUser == null) {
            return "redirect:/accounts/login"; // Chuyển hướng về trang đăng nhập
        }

        // Lấy userId từ tài khoản đã đăng nhập
        Long userId = loggedInUser.getId();

        // Lấy giỏ hàng của người dùng
        Cart cart = cartService.getOrCreateCart(userId);

        // Thêm giỏ hàng vào model để hiển thị trong view
        model.addAttribute("cart", cart);

        return "checkout";
    }


    @PostMapping
    public String processCheckout(@RequestParam String paymentMethod,
                                  @RequestParam String address,
                                  @RequestParam String shippingMethod,
                                  @ModelAttribute("loggedInUser") Account loggedInUser,
                                  Model model) {
        // Kiểm tra nếu người dùng chưa đăng nhập
        if (loggedInUser == null) {
            return "redirect:/accounts/login"; // Chuyển hướng đến trang đăng nhập
        }

        // Lấy userId từ tài khoản đã đăng nhập
        Long userId = loggedInUser.getId();

        // Tạo đơn hàng
        Order order = orderService.createOrder(loggedInUser, paymentMethod, address, shippingMethod);

        // Gửi thông tin sang view để hiển thị xác nhận
        model.addAttribute("order", order);
        return "order-confirmation";
    }


}
