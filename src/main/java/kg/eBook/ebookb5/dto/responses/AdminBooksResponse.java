package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.models.Book;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminBooksResponse {

    private Long id;

    private String mainImage;

    private String name;

    private LocalDate publishedDate;

    private int price;

    private String bookType;

    public AdminBooksResponse() {
    }

    public AdminBooksResponse(Long id,
                              String mainImage,
                              String name,
                              LocalDate publishedDate,
                              int price,
                              BookType bookType) {
        this.id = id;
        this.mainImage = mainImage;
        this.name = name;
        this.publishedDate = publishedDate;
        this.price = price;
        this.bookType = bookType.name();
    }
}
