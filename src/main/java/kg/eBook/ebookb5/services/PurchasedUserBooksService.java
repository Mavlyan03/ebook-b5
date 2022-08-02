package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse;
import kg.eBook.ebookb5.models.PurchasedUserBooks;
import kg.eBook.ebookb5.repositories.PurchasedUserBooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchasedUserBooksService {

    private final PurchasedUserBooksRepository userBooksRepository;

    public List<PurchasedUserBooksResponse> purchasedUserBooks(Long userId) {
        return view(userBooksRepository.findAllByUserId(userId));
    }

    private List<PurchasedUserBooksResponse> view(List<PurchasedUserBooks> userBooks) {
        List<PurchasedUserBooksResponse> booksResponse = new ArrayList<>();
        for (PurchasedUserBooks userBook : userBooks) {
            booksResponse.add(new PurchasedUserBooksResponse(userBook));
        }
        return booksResponse;
    }
}
