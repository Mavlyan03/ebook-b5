package kg.eBook.ebookb5.dto.requests.books;

import kg.eBook.ebookb5.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSaveRequestGeneral {

    private String mainImage;

    private String secondImage;

    private String thirdImage;

    @NotEmpty(message = "Поле не может быть пустым")
    private String name;

    @NotNull(message = "Поле не может быть пустым")
    private Long genreId;

    @NotNull(message = "Поле не может быть пустым")
    private int price;

    @NotEmpty(message = "Поле не может быть пустым")
    private String author;

    @NotEmpty(message = "Поле не может быть пустым")
    private String description;

    @NotNull(message = "Поле не может быть пустым")
    private Language language;

    @NotNull(message = "Поле не может быть пустым")
    private int yearOfIssue;

    private int discount;

    private boolean bestseller;

    @NotEmpty(message = "Поле не может быть пустым")
    private String fragment;

}
