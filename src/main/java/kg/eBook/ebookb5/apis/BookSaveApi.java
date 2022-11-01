package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.dto.responses.books.BookResponse;
import kg.eBook.ebookb5.dto.responses.books.BookResponseGeneral;
import kg.eBook.ebookb5.db.services.BookService;
import kg.eBook.ebookb5.db.services.book.AudioBookService;
import kg.eBook.ebookb5.db.services.book.ElectronicBookService;
import kg.eBook.ebookb5.db.services.book.PaperBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/books")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Book API", description = "Book endpoints for Vendor & Admin")
public class BookSaveApi {

    private final PaperBookService paperBookService;
    private final ElectronicBookService eBookService;
    private final AudioBookService audioBookService;
    private final BookService bookService;

    @Operation(summary = "Save paper book", description = "Save paper book by vendor")
    @PreAuthorize("hasAuthority('VENDOR')")
    @PostMapping("paper-book")
    public BookResponse savePaperBook(Authentication authentication, @RequestBody PaperBookSaveRequest paperBook) {
        return paperBookService.savePaperBook(authentication, paperBook);
    }

    @Operation(summary = "Save eBook", description = "Save eBook by vendor")
    @PreAuthorize("hasAuthority('VENDOR')")
    @PostMapping("e-book")
    public BookResponse saveEbook(Authentication authentication, @RequestBody ElectronicBookSaveRequest eBook) {
        return eBookService.saveElectronicBook(authentication, eBook);
    }

    @Operation(summary = "Save audio book", description = "Save audio book by vendor")
    @PreAuthorize("hasAuthority('VENDOR')")
    @PostMapping("audio-book")
    public BookResponse saveAudioBook(Authentication authentication, @RequestBody AudioBookSaveRequest audioBook) {
        return audioBookService.saveAudioBook(authentication, audioBook);
    }

    @Operation(summary = "Remove book", description = "Delete any books by vendor&admin")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    @DeleteMapping("{bookId}")
    public SimpleResponse deleteBook(Authentication authentication, @PathVariable Long bookId) {
        paperBookService.deleteBook(authentication, bookId);
        return new SimpleResponse("Книга успешно удалена!");
    }

    @Operation(summary = "Update paper book", description = "Update paper book by vendor&admin")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    @PutMapping("paper-book/{bookId}")
    public SimpleResponse updatePaperBook(Authentication authentication, @PathVariable Long bookId,
                                          @RequestBody @Valid PaperBookSaveRequest paperBook) {
        paperBookService.updateBook(authentication, bookId, paperBook);
        return new SimpleResponse("Книга успешно обновлена!");
    }

    @Operation(summary = "Update eBook", description = "Update eBook by vendor&admin")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    @PutMapping("e-book/{bookId}")
    public SimpleResponse updateEBook(Authentication authentication, @PathVariable Long bookId,
                                              @RequestBody @Valid ElectronicBookSaveRequest eBook) {
        eBookService.updateBook(authentication, bookId, eBook);
        return new SimpleResponse("Книга успешно обновлена!");
    }

    @Operation(summary = "Update audio book", description = "Update audio book by vendor&admin")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    @PutMapping("audio-book/{bookId}")
    public SimpleResponse updateEBook(Authentication authentication, @PathVariable Long bookId,
                                              @RequestBody @Valid AudioBookSaveRequest audioBook) {
        audioBookService.updateBook(authentication, bookId, audioBook);
        return new SimpleResponse("Книга успешно обновлена!");
    }

    @Operation(summary = "Get book by id", description = "Get book with id by vendor&admin")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    @GetMapping("{bookId}")
    public List<? extends BookResponseGeneral> findBookById(@PathVariable Long bookId) {
        return bookService.findBookById(bookId);
    }

}
