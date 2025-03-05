package bisosad.kt2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "id")
    private Product product;

    @Column(nullable = false)
    private int rating; // Giá trị từ 1 đến 5

    @Column(length = 255)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime feedbackDate;
}
