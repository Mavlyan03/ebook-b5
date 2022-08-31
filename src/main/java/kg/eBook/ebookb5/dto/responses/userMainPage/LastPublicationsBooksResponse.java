package kg.eBook.ebookb5.dto.responses.userMainPage;

import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class LastPublicationsBooksResponse extends BestsellerBooksResponse {

    private Genre genre;

    public LastPublicationsBooksResponse(Book books, Genre genre) {
        super(books);
        this.genre = genre;
    }

    public static List<LastPublicationsBooksResponse> viewLastPublicationsMain(List<Book> bookList) {
        List<LastPublicationsBooksResponse> lastPublicationsBooksResponses = new ArrayList<>();
        for (Book book : bookList) {
            lastPublicationsBooksResponses.add(new LastPublicationsBooksResponse(book, book.getGenre()));
        }
        return lastPublicationsBooksResponses;
    }
}
