package bisosad.kt2.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
public class Order {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;




    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    private BigDecimal totalAmount;

    private LocalDateTime orderDate;

    private String status;

    @Column(nullable = false)
    private String shippingMethod; // Thêm trường shippingMethod

    @Column(nullable = false)
    private String address; // Thêm trường address

    // Constructors
    public Order() {}

    public Order(User user, Cart cart, BigDecimal totalAmount, LocalDateTime orderDate, String status, String shippingMethod, String address) {
        this.user = user;
        this.cart = cart;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
        this.shippingMethod = shippingMethod;
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
