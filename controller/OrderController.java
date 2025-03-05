package bisosad.kt2.controller;

import bisosad.kt2.model.Order;
import bisosad.kt2.service.OrderService;
import bisosad.kt2.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/list"; // Page to display list of orders
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            model.addAttribute("payment", paymentService.getPaymentByOrderId(id)); // Lấy thông tin thanh toán
            return "orders/detail"; // Trang hiển thị chi tiết
        } else {
            model.addAttribute("error", "Order not found");
            return "orders/error"; // Trang báo lỗi
        }
    }


    @GetMapping("/create")
    public String createOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orders/create"; // Page to create a new order
    }



    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "orders/edit"; // Page to edit the order
        } else {
            model.addAttribute("error", "Order not found");
            return "orders/error"; // Error page
        }
    }

    @PostMapping("/edit/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order order) {
        orderService.updateOrder(id, order);
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
