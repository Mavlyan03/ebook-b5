package kg.eBook.ebookb5.dto.responses.userMainPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteAudioBooksResponse extends FavoriteBooksResponse {

    private LocalTime duration;

    public FavoriteAudioBooksResponse(Long bookId, String mainImage, String name, String author, int price, LocalTime duration) {
        super(bookId, mainImage, name, author, price);
        this.duration = duration;
    }
}
