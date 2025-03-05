package bisosad.kt2.controller;

import bisosad.kt2.model.Account;
import bisosad.kt2.model.Cart;
import bisosad.kt2.model.User;
import bisosad.kt2.repository.AccountRepository;
import bisosad.kt2.service.AccountService;
import bisosad.kt2.service.CartService;
import bisosad.kt2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "accounts/login"; // Hiển thị trang login
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Optional<Account> user = userService.authenticate(username, password);

        // Log thông tin người dùng
        System.out.println("Đang kiểm tra đăng nhập với username: " + username);

        if (user.isPresent()) { // Kiểm tra user.isPresent() thay vì null
            // Lưu thông tin user vào session
            session.setAttribute("loggedInUser", user.get());
            Cart cart = cartService.getOrCreateCart(user.get().getId());
            session.setAttribute("cart", cart);
            System.out.println("Đăng nhập thành công! Tài khoản: " + user.get().getUsername());
            System.out.println("Session loggedInUser: " + session.getAttribute("loggedInUser"));

            // Kiểm tra vai trò của người dùng, nếu là ADMIN chuyển hướng đến trang admin
            if ("ADMIN".equals(user.get().getRole())) {
                return "redirect:/admin"; // Chuyển hướng đến trang admin
            } else {
                return "redirect:/"; // Nếu không phải admin, chuyển hướng đến trang chủ
            }

        } else {
            // Log nếu đăng nhập thất bại
            System.out.println("Đăng nhập thất bại với username: " + username);
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "accounts/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "accounts/login"; // Hiển thị trang đăng ký
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String name,
                           @RequestParam String address,
                           @RequestParam String phone,
                           @RequestParam String dateOfBirth,
                           Model model) {
        try {
            System.out.println("Bắt đầu đăng ký tài khoản...");

            // Tạo tài khoản mới
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setRole("USER");

            System.out.println("Tạo tài khoản với thông tin: " + account);

            // Tạo User mới và liên kết với Account
            User user = new User();
            user.setName(name);
            user.setAddress(address);
            user.setPhone(phone);
            user.setAccount(account);
            user.setDateOfBirth(dateOfBirth);

            System.out.println("Tạo người dùng với thông tin: " + user);

            // Lưu cả Account và User
            account.setUser(user); // Thiết lập liên kết hai chiều
            accountRepository.save(account); // Cascade sẽ tự động lưu User

            System.out.println("Tài khoản và người dùng đã được lưu vào cơ sở dữ liệu!");

            model.addAttribute("message", "Đăng ký tài khoản thành công!");
            return "redirect:/accounts/login";

        } catch (Exception e) {
            System.err.println("Đã xảy ra lỗi khi đăng ký tài khoản: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Đã xảy ra lỗi trong quá trình đăng ký. Vui lòng thử lại!");
            return "accounts/register"; // Điều hướng lại trang đăng ký
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa trạng thái đăng nhập
        session.invalidate();
        return "redirect:/accounts/login"; // Quay về trang login
    }
}
