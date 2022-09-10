package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse;
import kg.eBook.ebookb5.models.PurchasedUserBooks;
import kg.eBook.ebookb5.repositories.PurchasedUserBooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchasedUserBooksService {

    private final PurchasedUserBooksRepository userBooksRepository;

    public Page<PurchasedUserBooksResponse> purchasedUserBooks(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return userBooksRepository.findAllByUserId(userId, pageable);
    }
}
