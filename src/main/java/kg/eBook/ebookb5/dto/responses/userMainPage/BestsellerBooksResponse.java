package kg.eBook.ebookb5.dto.responses.userMainPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BestsellerBooksResponse {

    private Long bookId;
    private String name;
    private String mainImage;
    private String description;
    private int price;
}
