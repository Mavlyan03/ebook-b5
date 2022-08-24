package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.PromocodeRequest;
import kg.eBook.ebookb5.dto.responses.BookBasketResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.InvalidDateException;
import kg.eBook.ebookb5.exceptions.InvalidPromocodeException;
import kg.eBook.ebookb5.models.*;
import kg.eBook.ebookb5.repositories.PromocodeRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromocodeService {

    private final PromocodeRepository promocodeRepository;
    private final UserRepository userRepository;

    public SimpleResponse createPromoCode(PromocodeRequest promoCodeRequest, Authentication authentication) {

        if (promoCodeRequest.getDateOfStart().isAfter(promoCodeRequest.getDateOfFinish())) {
            throw new InvalidDateException("Дата, которую вы написали, более ранняя, чем дата начала");
        } else if (promoCodeRequest.getDateOfStart().plusDays(1L).isAfter(promoCodeRequest.getDateOfFinish())) {
            throw new InvalidDateException(
                    "Разница между началом и окончанием действия промокода должна быть более 1 дня");
        }

        if (promocodeRepository.existsByName(promoCodeRequest.getName())) {
            throw new AlreadyExistException("Уже существует с таким названием");
        }

        Promocode promocode = new Promocode(promoCodeRequest);
        User user = userRepository.findByEmail(authentication.getName()).get();
        promocode.setVendor(user);
        promocodeRepository.save(promocode);
        return new SimpleResponse("Промокод успешно создан!");
    }

    public List<BookBasketResponse> findAllBooksWithPromocode(String promocodeName, Authentication authentication) {

        Promocode promocode = promocodeRepository.findByName(promocodeName).orElseThrow(
                () -> new InvalidPromocodeException("Данный промокод не действителен"));

        if (LocalDate.now().isAfter(promocode.getDateOfFinish())) {
            throw new InvalidDateException("Срок действия промокода истек");
        }

        User client = userRepository.findByEmail(authentication.getName()).get();

        String discountPromocode = "";
        List<Long> bookId = new ArrayList<>();
        if (!thisPromocodeAppliesToBooks(client, promocode.getVendor())) {
            throw new InvalidPromocodeException("Данный промокод не действителен");
        } else {
            for (Book book : client.getUserBasket()) {
                for (Book vendorBook : promocode.getVendor().getBooks()) {
                    if (vendorBook.equals(book)) {
                        if (book.getDiscount() <= 0) {
                            int bookPrice = book.getPrice();
                            int priceDiscount = bookPrice * promocode.getDiscount() / 100;
                            int newPrice = bookPrice - priceDiscount;
                            book.setPrice(newPrice);
                            discountPromocode = "Промокод " + promocode.getDiscount() + " %";
                            bookId.add(book.getId());
                        }
                    }
                }
            }
        }
        return viewMapper(client.getUserBasket(), discountPromocode, bookId);
    }

    private boolean thisPromocodeAppliesToBooks(User client, User vendor) {
        for (Book book : client.getUserBasket()) {
            for (Book vendorBook : vendor.getBooks()) {
                if (vendorBook.equals(book)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<BookBasketResponse> viewMapper(List<Book> books, String discountPromocode, List<Long> bookId) {
        List<BookBasketResponse> basketResponses = new ArrayList<>();
        for (Book book : books) {
            basketResponses.add(new BookBasketResponse(book));
        }
        for (BookBasketResponse basketRespons : basketResponses) {
            for (Long aLong : bookId) {
                if (basketRespons.getId().equals(aLong)) {
                    basketRespons.setPromocode(discountPromocode);
                }
            }
        }
        return basketResponses;
    }

}
