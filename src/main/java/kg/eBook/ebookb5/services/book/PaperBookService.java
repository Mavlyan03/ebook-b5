package kg.eBook.ebookb5.services.book;

import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
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
public class PaperBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void savePaperBook(Authentication authentication, PaperBookSaveRequest paperBookSaveRequest, TypeOfBook typeOfBook) {

        isBookExists(paperBookSaveRequest, typeOfBook);

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

//        return new BookResponse(
//                book.getId(),
//                book.getMainImage(),
//                book.getName(),
//                book.getPrice(),
//                book.getYearOfIssue()
//        );
    }

    private void isBookExists(PaperBookSaveRequest paperBookSaveRequest, TypeOfBook typeOfBook) {

        Book book = bookRepository.findByName(paperBookSaveRequest.getName()).orElse(null);

        if(book != null) {
            if(book.getGenre().equals(paperBookSaveRequest.getGenre()) &&
            book.getLanguage().equals(paperBookSaveRequest.getLanguage()))
                throw new AlreadyExistException("This book is already exists");
        }

    }

    //update
    //delete

}
