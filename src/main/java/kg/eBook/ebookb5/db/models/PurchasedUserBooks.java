package kg.eBook.ebookb5.db.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "purchased_user_books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchasedUserBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchased_gen")
    @SequenceGenerator(name = "purchased_gen", sequenceName = "purchased_seq", initialValue = 11, allocationSize = 1)
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

    private int discount;

}
