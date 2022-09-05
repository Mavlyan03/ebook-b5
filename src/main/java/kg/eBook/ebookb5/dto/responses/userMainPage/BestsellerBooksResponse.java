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
//todo This response for all bestseller books and electronic books
public class BestsellerBooksResponse {

    private Long bookId;
    private String mainImage;
    private String description;
    private int price;


    public BestsellerBooksResponse(Book books) {
        this.bookId = books.getId();
        this.mainImage = books.getMainImage();
        this.description = books.getDescription();
        this.price = books.getPrice();
    }

    public static List<BestsellerBooksResponse> viewBestsellerMain(List<Book> bookList) {
        List<BestsellerBooksResponse> bestsellerBooksResponses = new ArrayList<>();
        for (Book book : bookList) {
            bestsellerBooksResponses.add(new BestsellerBooksResponse(book));
        }
        return bestsellerBooksResponses;
    }
}
