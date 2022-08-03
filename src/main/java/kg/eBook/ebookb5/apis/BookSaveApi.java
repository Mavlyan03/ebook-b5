package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.dto.responses.books.BookResponse;
import kg.eBook.ebookb5.services.book.AudioBookService;
import kg.eBook.ebookb5.services.book.ElectronicBookService;
import kg.eBook.ebookb5.services.book.PaperBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
@CrossOrigin
public class BookSaveApi {

    private final PaperBookService paperBookService;
    private final ElectronicBookService eBookService;
    private final AudioBookService audioBookService;

    @PostMapping("/save/paperBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public BookResponse savePaperBook(Authentication authentication, @RequestBody PaperBookSaveRequest paperBook) {
        return paperBookService.savePaperBook(authentication, paperBook);
    }

    @PostMapping("/save/eBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public BookResponse saveEbook(Authentication authentication, @RequestBody ElectronicBookSaveRequest eBook) {
        return eBookService.saveElectronicBook(authentication, eBook);

    }

    @PostMapping("/save/audioBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public BookResponse saveAudioBook(Authentication authentication, @RequestBody AudioBookSaveRequest audioBook) {
        return audioBookService.saveAudioBook(authentication, audioBook);
    }

    @DeleteMapping("/delete/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> deleteBook(Authentication authentication, @PathVariable Long bookId) {
        paperBookService.deleteBook(authentication, bookId);
        return ResponseEntity.ok("Книга успешно удалена!");
    }

    @PutMapping("/UpdatePaperBook/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> updatePaperBook(Authentication authentication, @PathVariable Long bookId, @RequestBody PaperBookSaveRequest paperBook) {
        paperBookService.updateBook(authentication, bookId, paperBook);
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

    @PutMapping("/UpdateElectronicBook/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> updateEBook(Authentication authentication, @PathVariable Long bookId, @RequestBody ElectronicBookSaveRequest eBook) {
        eBookService.updateBook(authentication, bookId, eBook);
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

    @PutMapping("/UpdateAudioBook/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> updateEBook(Authentication authentication, @PathVariable Long bookId, @RequestBody AudioBookSaveRequest audioBook) {
        audioBookService.updateBook(authentication, bookId, audioBook);
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

}
