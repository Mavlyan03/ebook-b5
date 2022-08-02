package kg.eBook.ebookb5.services.book;

import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
import kg.eBook.ebookb5.enums.TypeOfBook;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AudioBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void saveAudioBook(Authentication authentication, AudioBookSaveRequest audioBook, TypeOfBook typeOfBook) {
        isBookExists(audioBook, typeOfBook);

        Book book = new Book(

        );

        User user = userRepository.findByEmail(authentication.getName()).get();
        book.setOwner(user);
        user.setBook(book);

//        return new BookResponse(
//                book.getId(),
//                book.getMainImage(),
//                book.getName(),
//                book.getPrice(),
//                book.getYearOfIssue()
//        );
    }

    private void isBookExists(AudioBookSaveRequest audioBook, TypeOfBook typeOfBook) {

        Book book = bookRepository.findByName(audioBook.getName()).orElse(null);

        if(book != null) {
            if(book.getGenre().equals(audioBook.getGenre()) &&
                    book.getLanguage().equals(audioBook.getLanguage()))
                throw new AlreadyExistException("This book is already exists");
        }

    }

    public void deleteAudioBook(Long id) {

    }

}
