package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WishListService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void addBookToWishList(Long bookId, Authentication authentication) {

        User user1 = userRepository.findByEmail(authentication.getName()).get();

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Book with id: " + bookId + "not found"
        ));

        user1.setFavoriteBook(book);
        book.setUserToBook(user1);
    }

    public List<Book> getBooks(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).get();

        return user.getFavorite();
    }
}
