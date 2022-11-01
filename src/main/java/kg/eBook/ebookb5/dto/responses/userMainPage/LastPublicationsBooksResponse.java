package kg.eBook.ebookb5.dto.responses.userMainPage;

import kg.eBook.ebookb5.db.models.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LastPublicationsBooksResponse extends BestsellerBooksResponse {

    private String genre;

    public LastPublicationsBooksResponse(Long bookId, String name, String mainImage, String description, int price, Genre genre) {
        super(bookId, name, mainImage, description, price);
        this.genre = genre.getName();
    }
}