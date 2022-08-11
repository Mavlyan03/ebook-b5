package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.dto.responses.books.BookResponse;
import kg.eBook.ebookb5.dto.responses.books.BookResponseGeneral;
import kg.eBook.ebookb5.services.BookService;
import kg.eBook.ebookb5.services.book.AudioBookService;
import kg.eBook.ebookb5.services.book.ElectronicBookService;
import kg.eBook.ebookb5.services.book.PaperBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
@CrossOrigin
public class BookSaveApi {

    private final PaperBookService paperBookService;
    private final ElectronicBookService eBookService;
    private final AudioBookService audioBookService;
    private final BookService bookService;

    @PostMapping("/save/paperBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public BookResponse savePaperBook(Authentication authentication, @RequestBody @Valid PaperBookSaveRequest paperBook) {
        return paperBookService.savePaperBook(authentication, paperBook);
    }

    @PostMapping("/save/eBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public BookResponse saveEbook(Authentication authentication, @RequestBody @Valid ElectronicBookSaveRequest eBook) {
        return eBookService.saveElectronicBook(authentication, eBook);
    }

    @PostMapping("/save/audioBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public BookResponse saveAudioBook(Authentication authentication, @RequestBody @Valid AudioBookSaveRequest audioBook) {
        return audioBookService.saveAudioBook(authentication, audioBook);
    }

    @DeleteMapping("/delete/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> deleteBook(Authentication authentication, @PathVariable Long bookId) {
        paperBookService.deleteBook(authentication, bookId);
        return ResponseEntity.ok("Книга успешно удалена!");
    }

    @PutMapping("/update/paperBook/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> updatePaperBook(Authentication authentication, @PathVariable Long bookId,
                                                  @RequestBody @Valid PaperBookSaveRequest paperBook) {
        paperBookService.updateBook(authentication, bookId, paperBook);
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

    @PutMapping("/update/electronicBook/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> updateEBook(Authentication authentication, @PathVariable Long bookId,
                                              @RequestBody @Valid ElectronicBookSaveRequest eBook) {
        eBookService.updateBook(authentication, bookId, eBook);
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

    @PutMapping("/update/audioBook/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> updateEBook(Authentication authentication, @PathVariable Long bookId,
                                              @RequestBody @Valid AudioBookSaveRequest audioBook) {
        audioBookService.updateBook(authentication, bookId, audioBook);
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

    @GetMapping("/find/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public List<? extends BookResponseGeneral> findBookById(@PathVariable Long bookId) {
        return bookService.finbBookById(bookId);
    }
}
