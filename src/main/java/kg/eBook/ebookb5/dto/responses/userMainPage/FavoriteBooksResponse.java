package kg.eBook.ebookb5.dto.responses.userMainPage;

import kg.eBook.ebookb5.models.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


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

    public static List<FavoriteBooksResponse> mapperFavoriteBooksResponse(List<Book> books) {
        List<FavoriteBooksResponse> favoriteBooksResponses = new ArrayList<>();
        for (Book book : books) {
            favoriteBooksResponses.add(new FavoriteBooksResponse(
                    book.getId(), book.getMainImage(), book.getName(), book.getAuthor(), book.getPrice()));
        }
        return favoriteBooksResponses;
    }
}
