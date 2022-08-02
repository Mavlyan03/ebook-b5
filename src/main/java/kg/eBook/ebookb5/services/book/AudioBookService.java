package kg.eBook.ebookb5.services.book;

import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
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
public class AudioBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void saveAudioBook(Authentication authentication, AudioBookSaveRequest audioBook) {
        isBookExists(audioBook);

        Book book = new Book(
            audioBook.getName(),
            audioBook.getGenre(),
            audioBook.getPrice(),
            audioBook.getAuthor(),
            audioBook.getDescription(),
            audioBook.getLanguage(),
            audioBook.getYearOfIssue(),
            audioBook.getDiscount(),
            audioBook.isBestseller(),
            audioBook.getMainImage(),
            audioBook.getSecondImage(),
            audioBook.getThirdImage(),
            audioBook.getFragment(),
            audioBook.getDuration(),
            audioBook.getAudioBook()
        );

        User user = userRepository.findByEmail(authentication.getName()).get();
        book.setOwner(user);
        user.setBook(book);

        ResponseEntity.ok(HttpStatus.CREATED);
    }

    private void isBookExists(AudioBookSaveRequest audioBook) {

        Book book = bookRepository.findByName(audioBook.getName()).orElse(null);

        if(book != null) {
            if(book.getGenre().equals(audioBook.getGenre()) &&
                    book.getLanguage().equals(audioBook.getLanguage()) &&
                        book.getTypeOfBook().equals(AUDIO_BOOK))
                throw new AlreadyExistException("This book is already exists");
        }

    }

    public void updateBook(Authentication authentication, Long bookId, AudioBookSaveRequest audioBook) {

        User user = userRepository.findByEmail(authentication.getName()).get();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Book with id: " + bookId + " not found"));

        if(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.VENDOR) && book.getTypeOfBook().equals(AUDIO_BOOK)) {
            book.setName(audioBook.getName());
            book.setGenre(audioBook.getGenre());
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
            book.setDuration(audioBook.getDuration());
            book.setAudioBook(audioBook.getAudioBook());

            user.setBook(book);
            book.setOwner(user);
        } else
            throw new IllegalStateException("You cannot update this book!");
    }
}
