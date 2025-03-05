package bisosad.kt2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MobilePhone")
public class MobilePhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String model;

    @Column(length = 100)
    private String brand;

    @Column(length = 50)
    private String os;

    @Column(nullable = false)
    private int warrantyPeriod; // Warranty period in months
}
