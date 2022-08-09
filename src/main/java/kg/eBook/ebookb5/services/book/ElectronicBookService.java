package kg.eBook.ebookb5.services.book;

import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
import kg.eBook.ebookb5.dto.responses.books.BookResponse;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.GenreRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static kg.eBook.ebookb5.enums.BookType.ELECTRONIC_BOOK;
@Service
@RequiredArgsConstructor
@Transactional
public class ElectronicBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;

    public BookResponse saveElectronicBook(Authentication authentication, ElectronicBookSaveRequest eBook) {
        isBookExists(eBook);

        Book book = new Book(eBook);

        book.setBookType(ELECTRONIC_BOOK);

        book.setGenre(genreRepository.findById(eBook.getGenreId()).orElseThrow(() -> new NotFoundException(
                "Жанр с ID: " + eBook.getGenreId() + " не был найден"
        )));

        User user = userRepository.findByEmail(authentication.getName()).get();
        book.setOwner(user);
        user.setBook(book);

        Book savedBook = bookRepository.save(book);

        return new BookResponse(
                savedBook.getId(),
                savedBook.getName(),
                savedBook.getPrice(),
                savedBook.getYearOfIssue()
        );
    }

    private void isBookExists(ElectronicBookSaveRequest electronicBookSaveRequest) {

        Book book = bookRepository.findByName(electronicBookSaveRequest.getName()).orElse(null);

        if(book != null) {
            if(book.getLanguage().equals(electronicBookSaveRequest.getLanguage()) &&
                    book.getBookType().equals(ELECTRONIC_BOOK))
                throw new AlreadyExistException("Эта книга уже есть в базе");
        }

    }

    public ResponseEntity<HttpStatus> updateBook(Authentication authentication, Long bookId, ElectronicBookSaveRequest eBook) {
        User user = userRepository.findByEmail(authentication.getName()).get();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Книга с ID: " + bookId + " не найдена"));

        if(book.getBookType().equals(ELECTRONIC_BOOK)) {
            book.setName(eBook.getName());
            genreRepository.findById(eBook.getGenreId()).orElseThrow(() -> new NotFoundException(
                    "Жанр с ID: " + eBook.getGenreId() + " не найден"
            ));
            book.setPrice(eBook.getPrice());
            book.setAuthor(eBook.getAuthor());
            book.setPageSize(eBook.getPageSize());
            book.setPublishingHouse(eBook.getPublishingHouse());
            book.setDescription(eBook.getDescription());
            book.setLanguage(eBook.getLanguage());
            book.setYearOfIssue(eBook.getYearOfIssue());
            book.setDiscount(eBook.getDiscount());
            book.setBestseller(eBook.isBestseller());
            book.setMainImage(eBook.getMainImage());
            book.setSecondImage(eBook.getSecondImage());
            book.setThirdImage(eBook.getThirdImage());
            book.setFragment(eBook.getFragment());
            book.setElectronicBook(eBook.getElectronicBook());

            user.setBook(book);
            book.setOwner(user);
            return ResponseEntity.ok(HttpStatus.OK);
        } else
            throw new IllegalStateException("Вы не можете редактировать эту книгу");

    }
}
