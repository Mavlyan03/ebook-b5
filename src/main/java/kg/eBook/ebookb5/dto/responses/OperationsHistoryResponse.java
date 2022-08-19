package kg.eBook.ebookb5.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OperationsHistoryResponse {

    private List<PurchasedUserBooksResponse> purchasedBooks;
    private List<PurchasedUserBooksResponse> favoriteBooks;
    private List<PurchasedUserBooksResponse> basketBooks;
}
