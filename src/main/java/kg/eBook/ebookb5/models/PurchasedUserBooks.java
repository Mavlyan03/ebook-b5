package kg.eBook.ebookb5.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchased_user_books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchasedUserBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookName;

    private int price;

    private String bookAuthor;

    private LocalDate purchaseDate;

    private int quantityOfBook;

    private Long bookId;
    @ManyToOne
    private User user;

    private String bookMainImage;

    private int promocode;
}
