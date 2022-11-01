package kg.eBook.ebookb5.db.services;

import kg.eBook.ebookb5.dto.requests.PromoCodeRequest;
import kg.eBook.ebookb5.dto.responses.BookBasketResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.InvalidDateException;
import kg.eBook.ebookb5.exceptions.InvalidPromocodeException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.db.models.Book;
import kg.eBook.ebookb5.db.models.PromoCode;
import kg.eBook.ebookb5.db.models.User;
import kg.eBook.ebookb5.db.repositories.BookRepository;
import kg.eBook.ebookb5.db.repositories.PromoCodeRepository;
import kg.eBook.ebookb5.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PromoCodeService {

    private final BookRepository bookRepository;
    private final PromoCodeRepository promocodeRepository;
    private final UserRepository userRepository;

    public SimpleResponse createPromoCode(PromoCodeRequest promoCodeRequest, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).get();
        if (promoCodeRequest.getDateOfStart().isAfter(promoCodeRequest.getDateOfFinish())) {
            throw new InvalidDateException("Дата, которую вы написали, более ранняя, чем дата начала");
        } else if (promoCodeRequest.getDateOfStart().plusDays(1L).isAfter(promoCodeRequest.getDateOfFinish())) {
            throw new InvalidDateException(
                    "Разница между началом и окончанием действия промокода должна быть более 1 дня");
        }
        if (promocodeRepository.existsByName(promoCodeRequest.getName())) {
            throw new AlreadyExistException("Уже существует с таким названием");
        }

        PromoCode promoCode = new PromoCode(promoCodeRequest);
        promoCode.setVendor(user);
        promocodeRepository.save(promoCode);

        log.info("Promo code successfully created!");
        return new SimpleResponse("Промокод успешно создан!");
    }

    public List<BookBasketResponse> findAllBooksWithPromoCode(String promoCodeName, Authentication authentication) {
        if (promoCodeName == null) {
            User client = userRepository.findByEmail(authentication.getName()).get();
            return viewMapper(client.getUserBasket(), null, null);
        }
        PromoCode promoCode = promocodeRepository.findByName(promoCodeName).orElseThrow(
                () -> new InvalidPromocodeException("Данный промокод не действителен"));

        if (LocalDate.now().isAfter(promoCode.getDateOfFinish())) {
            throw new InvalidDateException("Срок действия промокода истек");
        }

        User client = userRepository.findByEmail(authentication.getName()).get();
        String discountPromoCode = "";
        List<Long> bookId = new ArrayList<>();
        if (!thisPromocodeAppliesToBooks(client, promoCode.getVendor())) {
            throw new InvalidPromocodeException("Данный промокод не действителен");
        } else {
            for (Book book : client.getUserBasket()) {
                for (Book vendorBook : promoCode.getVendor().getBooks()) {
                    if (vendorBook.equals(book)) {
                        if (book.getDiscount() <= 0) {
                            int bookPrice = book.getPrice();
                            int priceDiscount = bookPrice * promoCode.getDiscount() / 100;
                            int newPrice = bookPrice - priceDiscount;
                            book.setPrice(newPrice);
                            discountPromoCode = "Промокод " + promoCode.getDiscount() + " %";
                            bookId.add(book.getId());
                        }
                    }
                }
            }
        }
        return viewMapper(client.getUserBasket(), discountPromoCode, bookId);
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
        if (bookId != null) {
            for (BookBasketResponse basketRespons : basketResponses) {
                for (Long aLong : bookId) {
                    if (basketRespons.getId().equals(aLong)) {
                        basketRespons.setPromoCode(discountPromocode);
                    }
                }
            }
        }
        return basketResponses;
    }

    public int increaseBooksToBuy(Long bookId, Integer quantity) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(
                "Книга с ID " + bookId + " не найдена"));

        if (book.getQuantityOfBook() < quantity) {
            throw new IllegalStateException("В наличии имеется книг: " + book.getQuantityOfBook());
        }
        return quantity;
    }

    public int decreaseBookToBuy(Long bookId, Integer quantity, Authentication authentication) {
        userRepository.findByEmail(authentication.getName()).get();
        bookRepository.findById(bookId).orElseThrow(() ->
                new NotFoundException("Книга с ID " + bookId + " не найдена"));

        if (1 > quantity) {
            throw new IllegalStateException("Вы не можете выбрать меньше одной книги");
        }
        return quantity;
    }

    public void addBookToBasketList(Long bookId, Authentication authentication) {
        User user1 = userRepository.findByEmail(authentication.getName()).get();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Книга с ID: " + bookId + " не найдена"
                ));

        for (Book i : user1.getUserBasket()) {
            if (i.getBookType().equals(book.getBookType()) &&
                    i.getLanguage().equals(book.getLanguage()) &&
                    i.getGenre().equals(book.getGenre()) &&
                    i.getName().equals(book.getName()))
                throw new AlreadyExistException("Эта книга уже добавлена в корзину");
        }

        user1.setBasketBook(book);
        book.setUserToBasket(user1);
        log.info("The book has been successfully added to the basket list");
    }

    public void removeBookToBasketList(Long bookId, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).get();
        bookRepository.detacheBasket(bookId, user.getId());
    }

    public void removeAllBooksToBasketList(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).get();
        bookRepository.detacheAllBasket(user.getId());
    }

}
