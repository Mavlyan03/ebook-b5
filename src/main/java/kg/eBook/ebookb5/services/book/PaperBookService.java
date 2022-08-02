package kg.eBook.ebookb5.services.book;

import com.sun.jdi.request.InvalidRequestStateException;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static kg.eBook.ebookb5.enums.TypeOfBook.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PaperBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void savePaperBook(Authentication authentication, PaperBookSaveRequest paperBookSaveRequest) {

        isBookExists(paperBookSaveRequest);

        Book book = new Book(
                paperBookSaveRequest.getMainImage(),
                paperBookSaveRequest.getSecondImage(),
                paperBookSaveRequest.getThirdImage(),
                paperBookSaveRequest.getName(),
                paperBookSaveRequest.getGenre(),
                paperBookSaveRequest.getPrice(),
                paperBookSaveRequest.getAuthor(),
                paperBookSaveRequest.getPageSize(),
                paperBookSaveRequest.getPublishingHouse(),
                paperBookSaveRequest.getDescription(),
                paperBookSaveRequest.getLanguage(),
                paperBookSaveRequest.getYearOfIssue(),
                paperBookSaveRequest.getQuantityOfBook(),
                paperBookSaveRequest.getDiscount(),
                paperBookSaveRequest.isBestseller()
        );

        User user = userRepository.findByEmail(authentication.getName()).get();
        book.setOwner(user);
        user.setBook(book);

        ResponseEntity.ok(HttpStatus.CREATED);
    }

    private void isBookExists(PaperBookSaveRequest paperBookSaveRequest) {

        Book book = bookRepository.findByName(paperBookSaveRequest.getName()).orElse(null);

        if(book != null) {
            if(book.getGenre().equals(paperBookSaveRequest.getGenre()) &&
                    book.getLanguage().equals(paperBookSaveRequest.getLanguage()) &&
                    book.getTypeOfBook().equals(PAPER_BOOK))
                throw new AlreadyExistException("This book is already exists");
        }

    }

    public void deleteBook(Authentication authentication, Long bookId) {

        User user = userRepository.findByEmail(authentication.getName()).get();

        if(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.VENDOR)) {
            bookRepository.deleteById(bookId);
        }
        else {
            throw new InvalidRequestStateException("You cannot delete this book!");
        }
        ResponseEntity.ok(HttpStatus.OK);
    }

    public void updateBook(Authentication authentication, Long bookId, PaperBookSaveRequest paperBook) {
        User user = userRepository.findByEmail(authentication.getName()).get();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Book with id: " + bookId + " not found"));

        if(user.getRole().equals(Role.ADMIN) || (user.getRole().equals(Role.VENDOR) && book.getTypeOfBook().equals(PAPER_BOOK))) {
            book.setMainImage(paperBook.getMainImage());
            book.setSecondImage(paperBook.getSecondImage());
            book.setThirdImage(paperBook.getThirdImage());
            book.setName(paperBook.getName());
            book.setAuthor(paperBook.getAuthor());
            book.setGenre(paperBook.getGenre());
            book.setPublishingHouse(paperBook.getPublishingHouse());
            book.setDescription(paperBook.getDescription());
            book.setFragment(paperBook.getFragment());
            book.setLanguage(paperBook.getLanguage());
            book.setYearOfIssue(paperBook.getYearOfIssue());
            book.setPageSize(paperBook.getPageSize());
            book.setQuantityOfBook(paperBook.getQuantityOfBook());
            book.setPrice(paperBook.getPrice());
            book.setDiscount(paperBook.getDiscount());
            book.setBestseller(paperBook.isBestseller());

            user.setBook(book);
            book.setOwner(user);

            ResponseEntity.ok(HttpStatus.OK);
        } else
            throw new IllegalStateException("You cannot update this book!");



    }
}
