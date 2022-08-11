package kg.eBook.ebookb5.dto.responses.books;

import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.models.Book;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class AudiobookResponse extends BookInnerPageResponse {

    private BookType bookType;
    private Long bookId;
    private String bookName;
    private String genre;
    private int price;
    private String author;
    private String publishingHouse;
    private String description;
    private Language language;
    private int yearOfIssue;
    private int discount;
    private boolean bestseller;
    private String mainImage;
    private String secondImage;
    private String thirdImage;
    private String audioBookFragment;
    private String audioBook;
    private LocalTime duration;
    private boolean isNew;
    private int likes;
    private int basket;
    private boolean isEnabled;

    private Long vendorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public AudiobookResponse(Book audioBook) {
        this.bookType = audioBook.getBookType();
        this.bookId = audioBook.getId();
        this.bookName = audioBook.getName();
        this.genre = audioBook.getGenre().getName();
        this.price = audioBook.getPrice();
        this.author = audioBook.getAuthor();
        this.publishingHouse = audioBook.getPublishingHouse();
        this.description = audioBook.getDescription();
        this.language = audioBook.getLanguage();
        this.yearOfIssue = audioBook.getYearOfIssue();
        this.discount = audioBook.getDiscount();
        this.bestseller = audioBook.isBestseller();
        this.mainImage = audioBook.getMainImage();
        this.secondImage = audioBook.getSecondImage();
        this.thirdImage = audioBook.getThirdImage();
        this.audioBookFragment = audioBook.getAudioBookFragment();
        this.audioBook = audioBook.getAudioBook();
        this.duration = audioBook.getDuration();
        this.isNew = audioBook.isNew();
        this.likes = audioBook.getLikes().size();
        this.basket = audioBook.getBookBasket().size();
        this.isEnabled = audioBook.isEnabled();

        this.vendorId = audioBook.getOwner().getId();
        this.firstName = audioBook.getOwner().getFirstName();
        this.lastName = audioBook.getOwner().getLastName();
        this.phoneNumber = audioBook.getOwner().getPhoneNumber();
    }
}
