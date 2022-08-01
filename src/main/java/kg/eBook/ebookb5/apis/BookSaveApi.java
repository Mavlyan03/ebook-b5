package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.services.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
@CrossOrigin
public class BookSaveApi {

    private final BookService bookService;

    @PostMapping("/save/paperBook")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VENDOR')")
    public String savePaperBook(Authentication authentication, @RequestBody PaperBookSaveRequest paperBookSaveRequest) {
        bookService.saveBook(authentication, paperBookSaveRequest);
        return "Ваш запрос был успешно отправлен!";
    }

}
