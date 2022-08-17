package kg.eBook.ebookb5.models;

import kg.eBook.ebookb5.enums.BookStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    private String description;

    private boolean read;

    @CreatedDate
    private LocalDate createdAt;
    @ManyToOne
    private User vendor;
}
