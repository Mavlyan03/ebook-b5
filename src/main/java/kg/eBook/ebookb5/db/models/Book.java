package kg.eBook.ebookb5.db.models;

import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_gen")
    @SequenceGenerator(name = "book_gen", sequenceName = "book_seq", initialValue = 21, allocationSize = 1)
    private Long id;

    private String name;

    @ManyToOne
    private Genre genre;

    private int price;

    private String author;

    private int pageSize;

    private String publishingHouse;

    @Column(length = 10000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Language language;

    private LocalDate publishedDate;

    private int yearOfIssue;

    private int quantityOfBook;

    private int discount;

    private boolean bestseller;

    private LocalDate dateTheBookWasAddedToFavorites;

    @ManyToOne(cascade = {PERSIST, MERGE, DETACH})
    private User owner;

    private String mainImage;

    private String secondImage;

    private String thirdImage;

    @Enumerated(EnumType.STRING)
    private BookType bookType;

    @Column(length = 10000)
    private String fragment;

    private String audioBookFragment;

    private LocalTime duration;

    private String audioBook;

    private String electronicBook;

    @ManyToMany(cascade = {MERGE, DETACH, REFRESH, PERSIST})
    @JoinTable(name = "users_favorite_books", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likes;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    private boolean isEnabled;

    @ManyToMany(cascade = {DETACH, REFRESH, MERGE, PERSIST})
    @JoinTable(name = "users_basket_books", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> bookBasket = new ArrayList<>();

    private boolean isNew;

    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void setUserToBook(User user) {
        this.likes.add(user);
    }

    public void setUserToBasket(User user) {
        this.bookBasket.add(user);
    }

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
        this.bookStatus = BookStatus.IN_PROCESSING;
        this.publishedDate = LocalDate.now();
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
        this.audioBookFragment = audioBook.getAudioBookFragment();
        this.audioBook = audioBook.getAudioBook();
        this.bookStatus = BookStatus.IN_PROCESSING;
        this.publishedDate = LocalDate.now();
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
        this.bookStatus = BookStatus.IN_PROCESSING;
        this.publishedDate = LocalDate.now();
        this.fragment = paperBook.getFragment();
    }

    public void removeUserFromBasket(User user) {
        this.bookBasket.remove(user);
    }

    public void removeUserFromLikes(User user) {
        this.likes.remove(user);
    }
}