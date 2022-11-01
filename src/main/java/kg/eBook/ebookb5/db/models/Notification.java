package kg.eBook.ebookb5.db.models;

import kg.eBook.ebookb5.enums.BookStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_gen")
    @SequenceGenerator(name = "notification_gen", sequenceName = "notification_seq", initialValue = 26, allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    private String description;

    private Long bookId;

    private boolean read;

    @CreatedDate
    private LocalDate createdAt;

    @ManyToOne
    private User vendor;

}
