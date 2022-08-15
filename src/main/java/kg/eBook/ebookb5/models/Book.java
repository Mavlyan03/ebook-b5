package kg.eBook.ebookb5.models;

import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @ManyToOne(cascade = {PERSIST, MERGE, DETACH})
    private User owner;

    private String mainImage;

    private String secondImage;

    private String thirdImage;

    @Enumerated(EnumType.STRING)
    private BookType bookType;

    private String fragment;

    private String audioBookFragment;

    private LocalTime duration;

    private String audioBook;

    private String electronicBook;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<User> likes;

    public void setUserToBook(User user) {
        this.likes.add(user);
    }

    private BookStatus bookStatus;

    private boolean isEnabled;

    @ManyToMany
    @JoinTable(name = "users_basket_books", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> bookBasket;

    private boolean isNew;

    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Book(ElectronicBookSaveRequest eBook) {
        this.name = eBook.getName();
        this.price = eBook.getPrice();
        this.author = eBook.getAuthor();
        this.pageSize = eBook.getPageSize();
        this.publishingHouse = eBook.getPublishingHouse();
        this.description = eBook.getDescription();
        this.language = eBook.getLanguage();
        this.yearOfIssue = eBook.getYearOfIssue();
        this.discount = eBook.getDiscount();
        this.bestseller = eBook.isBestseller();
        this.mainImage = eBook.getMainImage();
        this.secondImage = eBook.getSecondImage();
        this.thirdImage = eBook.getThirdImage();
        this.fragment = eBook.getFragment();
        this.electronicBook = eBook.getElectronicBook();
    }

    public Book(AudioBookSaveRequest audioBook) {
        this.name = audioBook.getName();
        this.price = audioBook.getPrice();
        this.author = audioBook.getAuthor();
        this.description = audioBook.getDescription();
        this.language = audioBook.getLanguage();
        this.yearOfIssue = audioBook.getYearOfIssue();
        this.discount = audioBook.getDiscount();
        this.bestseller = audioBook.isBestseller();
        this.mainImage = audioBook.getMainImage();
        this.secondImage = audioBook.getSecondImage();
        this.thirdImage = audioBook.getThirdImage();
        this.fragment = audioBook.getFragment();
        this.duration = LocalTime.parse(audioBook.getDuration(), timeFormatter);
        this.audioBook = audioBook.getAudioBook();
    }

    public Book(PaperBookSaveRequest paperBook) {
        this.mainImage = paperBook.getMainImage();
        this.secondImage = paperBook.getSecondImage();
        this.thirdImage = paperBook.getThirdImage();
        this.name = paperBook.getName();
        this.price = paperBook.getPrice();
        this.author = paperBook.getAuthor();
        this.pageSize = paperBook.getPageSize();
        this.publishingHouse = paperBook.getPublishingHouse();
        this.description = paperBook.getDescription();
        this.language = paperBook.getLanguage();
        this.yearOfIssue = paperBook.getYearOfIssue();
        this.quantityOfBook = paperBook.getQuantityOfBook();
        this.discount = paperBook.getDiscount();
        this.bestseller = paperBook.isBestseller();
    }

    public void removeUserFromBasket(User user) {
        this.bookBasket.remove(user);
    }

    public void removeUserFromLikes(User user) {
        this.likes.remove(user);
    }
}