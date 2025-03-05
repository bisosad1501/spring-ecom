package bisosad.kt2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false) // Ánh xạ trường `user` tới cột `user_id`
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems = new ArrayList<>();

    private String status; // Trạng thái của giỏ hàng

    private BigDecimal totalAmount = BigDecimal.ZERO; // Tổng giá trị của giỏ hàng

    // Constructor mặc định
    public Cart() {}

    // Constructor đầy đủ
    public Cart(User user, String status, BigDecimal totalAmount) {
        this.user = user;
        this.status = status;
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        recalculateTotalAmount();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
    }

    public void addCartItem(CartItem item) {
        if (item != null) {
            this.cartItems.add(item);
            BigDecimal itemTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            this.totalAmount = this.totalAmount.add(itemTotal);
        }
    }

    public void removeCartItem(CartItem item) {
        if (item != null && this.cartItems.contains(item)) {
            this.cartItems.remove(item);
            BigDecimal itemTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            this.totalAmount = this.totalAmount.subtract(itemTotal);
        }
    }

    public BigDecimal recalculateTotalAmount() {
        if (this.cartItems == null || this.cartItems.isEmpty()) {
            this.totalAmount = BigDecimal.ZERO;
        } else {
            this.totalAmount = this.cartItems.stream()
                    .filter(cartItem -> cartItem.getProduct() != null)
                    .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return this.totalAmount;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", cartItems=" + cartItems +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

