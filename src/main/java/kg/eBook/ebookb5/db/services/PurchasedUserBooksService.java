package kg.eBook.ebookb5.db.services;

import kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse;
import kg.eBook.ebookb5.db.repositories.PurchasedUserBooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchasedUserBooksService {

    private final PurchasedUserBooksRepository userBooksRepository;

    public Page<PurchasedUserBooksResponse> purchasedUserBooks(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        return userBooksRepository.findAllByUserId(userId, pageable);
    }
}
