package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.enums.BookType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookResponse {

    private Long id;

    private String mainImage;

    private String name;

    private String author;

    private int price;

    private String bookType;

    public BookResponse(Long id, String mainImage, String name, String author, int price, BookType bookType) {
        this.id = id;
        this.mainImage = mainImage;
        this.name = name;
        this.author = author;
        this.price = price;
        this.bookType = bookType.name();
    }
    public BookResponse() {
    }
}
