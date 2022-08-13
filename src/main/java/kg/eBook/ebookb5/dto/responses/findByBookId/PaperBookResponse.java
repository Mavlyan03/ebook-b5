package kg.eBook.ebookb5.dto.responses.findByBookId;

import kg.eBook.ebookb5.dto.responses.findByBookId.BookInnerPageResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.models.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaperBookResponse extends BookInnerPageResponse {

    private BookType bookType;
    private Long bookId;
    private String bookName;
    private String genre;
    private int price;
    private String author;
    private int pageSize;
    private String publishingHouse;
    private String description;
    private Language language;
    private int yearOfIssue;
    private int discount;
    private boolean bestseller;
    private String mainImage;
    private String secondImage;
    private String thirdImage;
    private String fragment;
    private boolean isNew;
    private int likes;
    private int basket;
    private boolean isEnabled;

    private Long vendorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public PaperBookResponse(Book eBook) {
        this.bookType = eBook.getBookType();
        this.bookId = eBook.getId();
        this.bookName = eBook.getName();
        this.genre = eBook.getGenre().getName();
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
        this.isNew = eBook.isNew();
        this.likes = eBook.getLikes().size();
        this.basket = eBook.getBookBasket().size();
        this.isEnabled = eBook.isEnabled();

        this.vendorId = eBook.getOwner().getId();
        this.firstName = eBook.getOwner().getFirstName();
        this.lastName = eBook.getOwner().getLastName();
        this.phoneNumber = eBook.getOwner().getPhoneNumber();
    }
}
