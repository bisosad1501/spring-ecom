package bisosad.kt2.model;

import jakarta.persistence.*;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String shippingMethod;

    @Column(nullable = false)
    private String status;

    // Default constructor
    public Shipment() {}

    // Full constructor
    public Shipment(Order order, String address, String shippingMethod, String status) {
        this.order = order;
        this.address = address;
        this.shippingMethod = shippingMethod;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
