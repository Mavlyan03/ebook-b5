package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.models.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


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

    private String promocode;

    public BookBasketResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.price = book.getPrice();
        this.discount = book.getDiscount();
        this.mainImage = book.getMainImage();
    }
}
