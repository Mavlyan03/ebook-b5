package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.requests.books.BookSave;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
@CrossOrigin
public class BookSaveApi {

    @PostMapping("/save/paperBook")
    public String savePaperBook(Authentication authentication, BookSave<PaperBookSaveRequest> paperBookSaveRequest) {



        return "Ваш запрос был успешно отправлен!";
    }

}
