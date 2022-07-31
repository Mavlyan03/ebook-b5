package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.TypeOfBook;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.Genre;
import kg.eBook.ebookb5.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookBasketResponse {

    private Long id;

    private String name;

    private int price;

    private String author;

    private int discount;

    private String mainImage;

    private StringBuilder promocode;

    public BookBasketResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.price = book.getPrice();
        this.discount = book.getDiscount();
        this.mainImage = book.getMainImage();
        this.promocode = book.getPromocode();
    }
}
