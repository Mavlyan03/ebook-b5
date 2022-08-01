package kg.eBook.ebookb5.services.book;

import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.models.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    public void saveBook(Authentication authentication, PaperBookSaveRequest paperBookSaveRequest) {
//        Book paperBook = authentication.getName().
    }

}
