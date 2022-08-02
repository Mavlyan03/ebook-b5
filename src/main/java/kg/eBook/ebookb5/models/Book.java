package kg.eBook.ebookb5.models;

import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.TypeOfBook;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
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

    @ManyToOne(cascade = {PERSIST, MERGE, DETACH, REMOVE})
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

    @ManyToMany
    private List<User> likes;

    private BookStatus bookStatus;

    private boolean isEnabled;

    @ManyToMany
    private List<User> bookBasket;

    public Book(String mainImage, String secondImage, String thirdImage, String name, Genre genre, int price, String author,
                int pageSize, String publishingHouse, String description, Language language, int yearOfIssue,
                int quantityOfBook, int discount, boolean bestseller) {
        this.mainImage = mainImage;
        this.secondImage = secondImage;
        this.thirdImage = thirdImage;
        this.name = name;
        this.genre = genre;
        this.price = price;
        this.author = author;
        this.pageSize = pageSize;
        this.publishingHouse = publishingHouse; //////////// Paper Book constructor
        this.description = description;
        this.language = language;
        this.yearOfIssue = yearOfIssue;
        this.quantityOfBook = quantityOfBook;
        this.discount = discount;
        this.bestseller = bestseller;
    }

    public Book(String name, Genre genre, int price, String author, int pageSize, String publishingHouse, String description,
                Language language, int yearOfIssue, int discount, boolean bestseller, String mainImage, String secondImage,
                String thirdImage, String fragment, String electronicBook) {
        this.name = name;
        this.genre = genre;
        this.price = price;
        this.author = author;
        this.pageSize = pageSize;
        this.publishingHouse = publishingHouse; //////////// Electronic Book constructor
        this.description = description;
        this.language = language;
        this.yearOfIssue = yearOfIssue;
        this.discount = discount;
        this.bestseller = bestseller;
        this.mainImage = mainImage;
        this.secondImage = secondImage;
        this.thirdImage = thirdImage;
        this.fragment = fragment;
        this.electronicBook = electronicBook;
    }
}
