package kg.eBook.ebookb5.dto.requests.books;

import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.models.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookSaveRequestGeneral {

    private String mainImage;

    private String secondImage;

    private String thirdImage;

    private String name;

    private Genre genre;

    private int price;

    private String author;

    private String description;

    private Language language;

    private int yearOfIssue;

    private int discount;

    private boolean bestseller;

    private String fragment;

}
