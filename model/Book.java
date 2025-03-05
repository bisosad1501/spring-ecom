package bisosad.kt2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 13)
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "categoryID", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "authorID", referencedColumnName = "id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisherID", referencedColumnName = "id")
    private Publisher publisher;

    @Column(length = 100)
    private String genre;

    private LocalDateTime releaseDate;
}
