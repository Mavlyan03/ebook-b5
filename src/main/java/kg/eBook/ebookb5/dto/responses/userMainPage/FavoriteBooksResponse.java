package kg.eBook.ebookb5.dto.responses.userMainPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteBooksResponse {

    private Long bookId;
    private String mainImage;
    private String name;
    private String author;
    private int price;

}
