package bisosad.kt2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Shoes")
public class Shoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "sizeID", referencedColumnName = "id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "typeID", referencedColumnName = "id")
    private Type type;

    @Column(length = 100)
    private String material;
}
