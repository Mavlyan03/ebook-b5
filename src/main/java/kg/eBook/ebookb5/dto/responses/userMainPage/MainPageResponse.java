package kg.eBook.ebookb5.dto.responses.userMainPage;

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
    private List<FavoriteAudioBooksResponse> favoriteAudioBookResponses;
    private List<BestsellerBooksResponse> favoriteElectronicBooksResponses;


}
