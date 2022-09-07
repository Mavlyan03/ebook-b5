package kg.eBook.ebookb5.dto.responses.userMainPage;

import kg.eBook.ebookb5.models.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LastPublicationsBooksResponse extends BestsellerBooksResponse {

    private String genre;

    public LastPublicationsBooksResponse(Long bookId, String mainImage, String description, int price, Genre genre) {
        super(bookId, mainImage, description, price);
        this.genre = genre.getName();
    }
}