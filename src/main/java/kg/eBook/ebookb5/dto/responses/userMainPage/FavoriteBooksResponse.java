package kg.eBook.ebookb5.dto.responses.userMainPage;

import kg.eBook.ebookb5.models.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class FavoriteBooksResponse {

    private Long bookId;
    private String mainImage;
    private String name;
    private String author;
    private int price;


    public FavoriteBooksResponse(Book books) {
        this.bookId = books.getId();
        this.mainImage = books.getMainImage();
        this.name = books.getName();
        this.author = books.getAuthor();
        this.price = books.getPrice();
    }

    public static List<FavoriteBooksResponse> viewFavoriteMain(List<Book> bookList) {
        List<FavoriteBooksResponse> favoriteBooksResponses = new ArrayList<>();
        for (Book book : bookList) {
            favoriteBooksResponses.add(new FavoriteBooksResponse(book));
        }
        return favoriteBooksResponses;
    }
}
