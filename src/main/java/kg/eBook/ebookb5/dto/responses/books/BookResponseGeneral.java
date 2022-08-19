package kg.eBook.ebookb5.dto.responses.books;

import kg.eBook.ebookb5.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseGeneral {

    private String mainImage;

    private String secondImage;

    private String thirdImage;

    private String name;

    private Long genreId;

    private int price;

    private String author;

    private String description;

    private Language language;

    private int yearOfIssue;

    private int discount;

    private boolean bestseller;

    private String fragment;

}
