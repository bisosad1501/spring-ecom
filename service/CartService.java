package bisosad.kt2.service;

import bisosad.kt2.model.*;
import bisosad.kt2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(Long userId, Long productId, int quantity) {
        Cart cart = getOrCreateCart(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        // Kiểm tra nếu sản phẩm đã tồn tại trong giỏ hàng
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // Tăng số lượng nếu sản phẩm đã tồn tại
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            // Tạo mới CartItem và gán vào giỏ hàng
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getCartItems().add(newItem);
        }

        recalculateCartTotal(cart); // Tính lại tổng giá trị giỏ hàng
        cartRepository.save(cart);
    }

    public Cart getOrCreateCart(Long accountId) {
        // Tìm Account trong cơ sở dữ liệu
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));

        // Tìm User liên kết với Account
        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("User not found for account ID: " + accountId));

        // Tìm giỏ hàng liên kết với User hoặc tạo mới nếu không tồn tại
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user); // Gắn User vào Cart
                    newCart.setStatus("active"); // Gắn trạng thái mặc định
                    return cartRepository.save(newCart); // Lưu Cart mới
                });
    }
    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public void updateCartItem(Long userId, Long cartItemId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));

        recalculateCartTotal(cart); // Tính lại tổng giá trị giỏ hàng
        cartRepository.save(cart);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeCartItem(Long userId, Long cartItemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        cart.getCartItems().remove(cartItem); // Xóa sản phẩm khỏi giỏ hàng
        recalculateCartTotal(cart); // Tính lại tổng giá trị giỏ hàng
        cartItemRepository.delete(cartItem); // Xóa sản phẩm khỏi DB
        cartRepository.save(cart);
    }



    // Tính lại tổng giá trị giỏ hàng
    private void recalculateCartTotal(Cart cart) {
        BigDecimal total = cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }
}
