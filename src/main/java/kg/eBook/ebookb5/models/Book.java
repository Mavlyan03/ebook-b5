package kg.eBook.ebookb5.models;

import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.TypeOfBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "book_gen", sequenceName = "book_seq", allocationSize = 1)
    private Long id;

    private String name;
    @ManyToOne
    private Genre genre;

    private int price;

    private String author;

    private int pageSize;

    private String publishingHouse;

    private String description;

    private Language language;

    private LocalDate publishedDate;

    private int yearOfIssue;

    private int quantityOfBook;

    private int discount;

    private boolean bestseller;

    private LocalDate dateTheBookWasAddedToFavorites;

    @ManyToOne
    private User owner;

    private String mainImage;

    private String secondImage;

    private String thirdImage;

    private TypeOfBook typeOfBook;

    private String fragment;

    private String audioBookFragment;

    private LocalTime duration;

    private String audioBook;

    private String electronicBook;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_favorite_books", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likes;

    public void setUserToBook(User user) {
        this.likes.add(user);
    }

    private BookStatus bookStatus;

    private boolean isEnabled;

    @ManyToMany(cascade = {DETACH, REFRESH, MERGE, PERSIST})
    private List<User> bookBasket;

    public void removeUserFromBasket(User user) {
        this.bookBasket.remove(user);
    }

    public void removeUserFromLikes(User user) {
        this.likes.remove(user);
    }
}
