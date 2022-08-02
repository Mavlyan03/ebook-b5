package kg.eBook.ebookb5.services.book;

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
public class ElectronicBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void saveElectronicBook(Authentication authentication, ElectronicBookSaveRequest eBook, TypeOfBook typeOfBook) {
        isBookExists(eBook, typeOfBook);

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

//        return new BookResponse(
//                book.getId(),
//                book.getMainImage(),
//                book.getName(),
//                book.getPrice(),
//                book.getYearOfIssue()
//        );
    }

    private void isBookExists(ElectronicBookSaveRequest electronicBookSaveRequest, TypeOfBook typeOfBook) {

        Book book = bookRepository.findByName(electronicBookSaveRequest.getName()).orElse(null);

        if(book != null) {
            if(book.getGenre().equals(electronicBookSaveRequest.getGenre()) &&
                    book.getLanguage().equals(electronicBookSaveRequest.getLanguage()))
                throw new AlreadyExistException("This book is already exists");
        }

    }

    //update
    //delete

}
