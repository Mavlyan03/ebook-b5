package kg.eBook.ebookb5.db.services;

import kg.eBook.ebookb5.dto.responses.userMainPage.FavoriteBooksResponse;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.db.models.Book;
import kg.eBook.ebookb5.db.models.User;
import kg.eBook.ebookb5.db.repositories.BookRepository;
import kg.eBook.ebookb5.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kg.eBook.ebookb5.dto.responses.userMainPage.FavoriteBooksResponse.mapperFavoriteBooksResponse;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WishListService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void addBookToWishList(Long bookId, Authentication authentication) {

        User user1 = userRepository.findByEmail(authentication.getName()).get();

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Книга с ID: " + bookId + " не найдена"
                ));

        for (Book i : user1.getFavorite()) {
            if (i.getBookType().equals(book.getBookType()) &&
                    i.getLanguage().equals(book.getLanguage()) &&
                    i.getGenre().equals(book.getGenre()) &&
                    i.getName().equals(book.getName()))

                throw new AlreadyExistException("Эта книга уже добавлена в избранное");
        }

        user1.setFavoriteBook(book);
        book.setUserToBook(user1);
        log.info("The book has been successfully added to the wishlist");
    }

    public List<FavoriteBooksResponse> getBooks(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).get();

        return mapperFavoriteBooksResponse(user.getFavorite());
    }

    public void removeBook(Long bookId, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).get();
        bookRepository.detache(bookId, user.getId());
    }
}
