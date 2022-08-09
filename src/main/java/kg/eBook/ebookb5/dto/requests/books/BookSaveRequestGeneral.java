package kg.eBook.ebookb5.dto.requests.books;

import kg.eBook.ebookb5.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSaveRequestGeneral {

    private String mainImage;

    private String secondImage;

    private String thirdImage;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max = 100)
    private String name;

    @NotNull(message = "Поле не может быть пустым")
    private Long genreId;

    @NotNull(message = "Поле не может быть пустым")
    private int price;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max = 100)
    private String author;

    @NotEmpty(message = "Поле не может быть пустым")
    private String description;

    @NotNull(message = "Поле не может быть пустым")
    private Language language;

    @NotNull
    private int yearOfIssue;

    private int discount;

    private boolean bestseller;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max = 10000)
    private String fragment;

}
