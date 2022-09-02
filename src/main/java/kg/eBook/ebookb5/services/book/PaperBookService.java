package kg.eBook.ebookb5.services.book;

import com.sun.jdi.request.InvalidRequestStateException;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.dto.responses.books.BookResponse;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.GenreRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static kg.eBook.ebookb5.enums.BookType.PAPER_BOOK;

@Service
@RequiredArgsConstructor
@Transactional
public class PaperBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final Logger logger = LoggerFactory.getLogger(ElectronicBookService.class);

    public BookResponse savePaperBook(Authentication authentication, PaperBookSaveRequest paperBook) {

        logger.info("Save paper book ...");
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

        logger.info("Peper book successfully saved");
        return new BookResponse(
                savedBook.getId(),
                savedBook.getName(),
                savedBook.getPrice(),
                savedBook.getYearOfIssue()
        );
    }

    private void isBookExists(PaperBookSaveRequest paperBookSaveRequest) {

        logger.info("Check if book exists");
        Book book = bookRepository.findByName(paperBookSaveRequest.getName()).orElse(null);

        if(book != null) {
            if(book.getLanguage().equals(paperBookSaveRequest.getLanguage()) &&
                    book.getBookType().equals(PAPER_BOOK))
                logger.error("This book is already in the database");
                throw new AlreadyExistException("Эта книга уже есть в базе");
        }

    }

    public void deleteBook(Authentication authentication, Long bookId) {

        logger.info("Delete book ...");
        User user = userRepository.findByEmail(authentication.getName()).get();

        if(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.VENDOR)) {
            bookRepository.deleteById(bookId);
            logger.info("Book successfully deleted");
        }
        else {
            logger.error("You cannot delete this book!");
            throw new InvalidRequestStateException("Вы не можете удалить эту книгу");
        }
        ResponseEntity.ok(HttpStatus.OK);
    }


    public void updateBook(Authentication authentication, Long bookId, PaperBookSaveRequest paperBook) {

        logger.info("Update paper book ...");
        User user = userRepository.findByEmail(authentication.getName()).get();

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Book with id: " + bookId + " not found"));

        if(book.getBookType().equals(PAPER_BOOK)) {
            book.setMainImage(paperBook.getMainImage());
            book.setSecondImage(paperBook.getSecondImage());
            book.setThirdImage(paperBook.getThirdImage());
            book.setName(paperBook.getName());
            book.setAuthor(paperBook.getAuthor());
            genreRepository.findById(paperBook.getGenreId()).orElseThrow(() -> new NotFoundException(
                    "Genre with id: " + paperBook.getGenreId() + " not found"
            ));
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

            logger.info("Paper book successfully updated");
            ResponseEntity.ok(HttpStatus.OK);
        } else
            logger.error("Vendor cannot edit this book!");
            throw new IllegalStateException("Вы не можете редактировать эту книгу");
    }
}
