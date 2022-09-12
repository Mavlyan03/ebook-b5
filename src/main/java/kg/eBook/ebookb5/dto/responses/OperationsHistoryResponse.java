package kg.eBook.ebookb5.dto.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class OperationsHistoryResponse {

    private Page<PurchasedUserBooksResponse> purchasedBooks;
    private int totalElementFavoriteBooks;
    private List<PurchasedUserBooksResponse> favoriteBooks;
    private int totalElementBasketBooks;
    private List<PurchasedUserBooksResponse> basketBooks;

    public OperationsHistoryResponse(Page<PurchasedUserBooksResponse> purchasedBooks,
                                     List<PurchasedUserBooksResponse> favoriteBooks,
                                     List<PurchasedUserBooksResponse> basketBooks) {
        this.purchasedBooks = purchasedBooks;
        this.totalElementFavoriteBooks = favoriteBooks.size();
        this.favoriteBooks = favoriteBooks;
        this.totalElementBasketBooks = basketBooks.size();
        this.basketBooks = basketBooks;
    }
}
