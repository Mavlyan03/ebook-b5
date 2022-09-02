package kg.eBook.ebookb5.services.book;

import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
import kg.eBook.ebookb5.dto.responses.GenreResponse;
import kg.eBook.ebookb5.dto.responses.books.BookResponse;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.GenreRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import kg.eBook.ebookb5.services.WishListService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static kg.eBook.ebookb5.enums.BookType.AUDIO_BOOK;

@Service
@RequiredArgsConstructor
@Transactional
public class AudioBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final Logger logger = LoggerFactory.getLogger(AudioBookService.class);

    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public BookResponse saveAudioBook(Authentication authentication, AudioBookSaveRequest audioBook) {

        logger.info("Save audio book ...");
        checkIfBookExists(audioBook);

        Book book = new Book(audioBook);

        book.setBookType(AUDIO_BOOK);

        book.setGenre(genreRepository.findById(audioBook.getGenreId()).orElseThrow(() -> new NotFoundException(
                "Жанр с ID: " + audioBook.getGenreId() + " не был найден"
        )));

        User user = userRepository.findByEmail(authentication.getName()).get();
        book.setOwner(user);
        user.setBook(book);

        Book savedBook = bookRepository.save(book);

        logger.info("Audio book successfully saved");
        return new BookResponse(
                savedBook.getId(),
                savedBook.getName(),
                savedBook.getPrice(),
                savedBook.getYearOfIssue()
        );
    }

    private void checkIfBookExists(AudioBookSaveRequest audioBook) {

        logger.info("Check if book exists");
        Book book = bookRepository.findByName(audioBook.getName()).orElse(null);

        if (book != null) {
            if (book.getLanguage().equals(audioBook.getLanguage()) && book.getBookType().equals(AUDIO_BOOK)) {
                logger.error("This book is already in the database");
                throw new AlreadyExistException("Эта книга уже есть в базе");
            }
        }
    }

    public void updateBook(Authentication authentication, Long bookId, AudioBookSaveRequest audioBook) {

        logger.info("Update audio book ...");
        User user = userRepository.findByEmail(authentication.getName()).get();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Книга с  ID: " + bookId + " не найдена"));

        if (book.getBookType().equals(AUDIO_BOOK)) {
            book.setName(audioBook.getName());
            genreRepository.findById(audioBook.getGenreId()).orElseThrow(() -> new NotFoundException(
                    "Жанр с  ID: " + audioBook.getGenreId() + " не найден"
            ));
            book.setPrice(audioBook.getPrice());
            book.setAuthor(audioBook.getAuthor());
            book.setDescription(audioBook.getDescription());
            book.setLanguage(audioBook.getLanguage());
            book.setYearOfIssue(audioBook.getYearOfIssue());
            book.setDiscount(audioBook.getDiscount());
            book.setBestseller(audioBook.isBestseller());
            book.setMainImage(audioBook.getMainImage());
            book.setSecondImage(audioBook.getSecondImage());
            book.setThirdImage(audioBook.getThirdImage());
            book.setFragment(audioBook.getFragment());
            book.setDuration(LocalTime.parse(audioBook.getDuration(), timeFormatter));
            book.setAudioBook(audioBook.getAudioBook());

            user.setBook(book);
            book.setOwner(user);

            logger.info("Book successfully updated");
        } else
            logger.error("Vendor cannot edit this book!");
            throw new IllegalStateException("Вы не можете редактировать эту книгу!");
    }
}
