package kg.eBook.ebookb5.services.book;

import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
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

import static kg.eBook.ebookb5.enums.Role.*;
import static kg.eBook.ebookb5.enums.TypeOfBook.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ElectronicBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ResponseEntity<HttpStatus> saveElectronicBook(Authentication authentication, ElectronicBookSaveRequest eBook) {
        isBookExists(eBook);

        Book book = new Book(
                eBook.getName(),
                eBook.getGenre(),
                eBook.getPrice(),
                eBook.getAuthor(),
                eBook.getPageSize(),
                eBook.getPublishingHouse(),
                eBook.getDescription(),
                eBook.getLanguage(),
                eBook.getYearOfIssue(),
                eBook.getDiscount(),
                eBook.isBestseller(),
                eBook.getMainImage(),
                eBook.getSecondImage(),
                eBook.getThirdImage(),
                eBook.getFragment(),
                eBook.getElectronicBook()
        );

        User user = userRepository.findByEmail(authentication.getName()).get();
        book.setOwner(user);
        user.setBook(book);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    private void isBookExists(ElectronicBookSaveRequest electronicBookSaveRequest) {

        Book book = bookRepository.findByName(electronicBookSaveRequest.getName()).orElse(null);

        if(book != null) {
            if(book.getGenre().equals(electronicBookSaveRequest.getGenre()) &&
                    book.getLanguage().equals(electronicBookSaveRequest.getLanguage()) &&
                    book.getTypeOfBook().equals(ELECTRONIC_BOOK))
                throw new AlreadyExistException("This book is already exists");
        }

    }

    public ResponseEntity<HttpStatus> updateBook(Authentication authentication, Long bookId, ElectronicBookSaveRequest eBook) {
        User user = userRepository.findByEmail(authentication.getName()).get();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Book with id: " + bookId + " not found"));

        if(user.getRole().equals(ADMIN) || (user.getRole().equals(VENDOR) && book.getTypeOfBook().equals(ELECTRONIC_BOOK))) {
            book.setName(eBook.getName());
            book.setGenre(eBook.getGenre());
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
            throw new IllegalStateException("You cannot update this book!");

    }
}
