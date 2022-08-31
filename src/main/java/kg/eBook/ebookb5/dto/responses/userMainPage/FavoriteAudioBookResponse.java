package kg.eBook.ebookb5.dto.responses.userMainPage;

import kg.eBook.ebookb5.models.Book;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FavoriteAudioBookResponse extends FavoriteBooksResponse {

    private LocalTime duration;

    public FavoriteAudioBookResponse(Book books, LocalTime duration) {
        super(books);
        this.duration = duration;
    }

    public static List<FavoriteAudioBookResponse> viewFavoriteAudioBooksMain(List<Book> bookList) {
        List<FavoriteAudioBookResponse> favoriteAudioBookResponses = new ArrayList<>();
        for (Book book : bookList) {
            favoriteAudioBookResponses.add(new FavoriteAudioBookResponse(book, book.getDuration()));
        }
        return favoriteAudioBookResponses;
    }
}
