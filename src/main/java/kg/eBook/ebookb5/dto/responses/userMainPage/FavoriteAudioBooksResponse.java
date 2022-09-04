package kg.eBook.ebookb5.dto.responses.userMainPage;

import kg.eBook.ebookb5.models.Book;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FavoriteAudioBooksResponse extends FavoriteBooksResponse {

    private LocalTime duration;

    public FavoriteAudioBooksResponse(Book books, LocalTime duration) {
        super(books);
        this.duration = duration;
    }

    public static List<FavoriteAudioBooksResponse> viewFavoriteAudioBooksMain(List<Book> bookList) {
        List<FavoriteAudioBooksResponse> favoriteAudioBookResponses = new ArrayList<>();
        for (Book book : bookList) {
            favoriteAudioBookResponses.add(new FavoriteAudioBooksResponse(book, book.getDuration()));
        }
        return favoriteAudioBookResponses;
    }
}
