package bisosad.kt2.service;

import bisosad.kt2.model.*;
import bisosad.kt2.repository.CartRepository;
import bisosad.kt2.repository.OrderRepository;
import bisosad.kt2.repository.PaymentRepository;
import bisosad.kt2.repository.ShipmentRepository;
import bisosad.kt2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    public Order createOrder(Account loggedInUser, String paymentMethod, String address, String shippingMethod) {
        // Lấy thông tin User từ loggedInUser
        User user = loggedInUser.getUser(); // Nếu Account liên kết với User

        if (user == null) {
            throw new RuntimeException("User not found for logged-in account: " + loggedInUser.getUsername());
        }

        // Lấy thông tin Cart của người dùng
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + user.getId()));

        // Tạo đối tượng Order
        Order order = new Order();
        order.setUser(user); // Gán user vào order
        order.setCart(cart); // Gán cart vào order
        order.setTotalAmount(cart.recalculateTotalAmount());
        order.setShippingMethod(shippingMethod);
        order.setAddress(address);
        order.setStatus("Pending");
        order.setOrderDate(LocalDateTime.now());

        // Lưu Order vào database
        orderRepository.save(order);

        // Tạo thông tin thanh toán
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(paymentMethod);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("Pending");

        // Lưu Payment vào database
        paymentRepository.save(payment);

        return order;
    }




    // Lấy tất cả các đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Lấy đơn hàng theo ID
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // Cập nhật đơn hàng
    public Order updateOrder(Long orderId, Order updatedOrder) {
        return orderRepository.findById(orderId).map(order -> {
            order.setStatus(updatedOrder.getStatus());
            order.setTotalAmount(updatedOrder.getTotalAmount());
            order.setCart(updatedOrder.getCart());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    // Xóa đơn hàng theo ID
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
    public Payment createPayment(Order order, String paymentMethod) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMethod(paymentMethod);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus("Pending");
        payment.setPaymentDate(LocalDateTime.now()); // Gán thời gian hiện tại

        return paymentRepository.save(payment);
}
}
