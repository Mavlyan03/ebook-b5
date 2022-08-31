package kg.eBook.ebookb5.dto.responses.userMainPage;

import kg.eBook.ebookb5.models.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainPageResponse {

    private List<FavoriteBooksResponse> favoriteBooksResponses;
    private List<BestsellerBooksResponse> bestsellerBooksResponses;
    private List<LastPublicationsBooksResponse> lastPublicationsBooksResponses;
    private List<FavoriteAudioBookResponse> favoriteAudioBookResponses;
    private List<BestsellerBooksResponse> electronicBooksResponses;

}
