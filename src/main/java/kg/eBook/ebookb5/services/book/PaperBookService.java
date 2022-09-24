package kg.eBook.ebookb5.services.book;


import com.sun.jdi.request.InvalidRequestStateException;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.dto.responses.books.BookResponse;
import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.GenreRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static kg.eBook.ebookb5.enums.BookType.PAPER_BOOK;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaperBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;

    public BookResponse savePaperBook(Authentication authentication, PaperBookSaveRequest paperBook) {

        isBookExists(paperBook);

        Book book = new Book(paperBook);

        book.setBookType(PAPER_BOOK);

        book.setGenre(genreRepository.findById(paperBook.getGenreId()).orElseThrow(() -> new NotFoundException(
                "Жанр с ID: " + paperBook.getGenreId() + " не был найден"
        )));

        User user = userRepository.findByEmail(authentication.getName()).get();

        book.setOwner(user);
        user.setBook(book);

        Book savedBook = bookRepository.save(book);

        log.info("Paper book successfully saved");
        return new BookResponse(
                savedBook.getId(),
                savedBook.getName(),
                savedBook.getPrice(),
                savedBook.getYearOfIssue()
        );
    }

    private void isBookExists(PaperBookSaveRequest paperBookSaveRequest) {

        Book book = bookRepository.findByName(paperBookSaveRequest.getName()).orElse(null);

        if(book != null) {
            if(book.getLanguage().equals(paperBookSaveRequest.getLanguage()) && book.getBookType().equals(PAPER_BOOK)) {
                throw new AlreadyExistException("Эта книга уже есть в базе");
            }
        }

    }

    public void deleteBook(Authentication authentication, Long bookId) {

        User user = userRepository.findByEmail(authentication.getName()).get();

        if(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.VENDOR)) {
            bookRepository.deleteById(bookId);

            log.info("Book successfully deleted");
        }
        else {
            throw new InvalidRequestStateException("Вы не можете удалить эту книгу");
        }
        ResponseEntity.ok(HttpStatus.OK);
    }


    public void updateBook(Authentication authentication, Long bookId, PaperBookSaveRequest paperBook) {

        User user = userRepository.findByEmail(authentication.getName()).get();

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Книга с идентификатором: " + bookId + " не найдена"));

        if(book.getBookType().equals(PAPER_BOOK)) {
            book.setMainImage(paperBook.getMainImage());
            book.setSecondImage(paperBook.getSecondImage());
            book.setThirdImage(paperBook.getThirdImage());
            book.setName(paperBook.getName());
            book.setAuthor(paperBook.getAuthor());
            book.setGenre(genreRepository.findById(paperBook.getGenreId()).orElseThrow(() -> new NotFoundException(
                    "Жанр с ID: " + paperBook.getGenreId() + " не был найден"
            )));
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
            book.setBookStatus(BookStatus.IN_PROCESSING);

            user.setBook(book);
            book.setOwner(user);

            log.info("Paper book successfully updated");
            ResponseEntity.ok(HttpStatus.OK);
        } else
            throw new IllegalStateException("Вы не можете редактировать эту книгу");
    }
}
