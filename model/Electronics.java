package bisosad.kt2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Electronics")
public class Electronics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 100)
    private String brand;

    @ManyToOne
    @JoinColumn(name = "typeID", referencedColumnName = "id")
    private Type type;

    @Column(nullable = false)
    private int warrantyPeriod; // Warranty period in months
}
