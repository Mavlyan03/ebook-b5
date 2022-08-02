package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.services.book.AudioBookService;
import kg.eBook.ebookb5.services.book.ElectronicBookService;
import kg.eBook.ebookb5.services.book.PaperBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static kg.eBook.ebookb5.enums.TypeOfBook.*;

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
    public ResponseEntity<String> savePaperBook(Authentication authentication, @RequestBody PaperBookSaveRequest paperBook) {
        paperBookService.savePaperBook(authentication, paperBook);
        return ResponseEntity.ok("Ваш запрос был успешно отправлен!");
    }

    @PostMapping("/save/eBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<String> saveEbook(Authentication authentication, @RequestBody ElectronicBookSaveRequest eBook) {
        eBookService.saveElectronicBook(authentication, eBook);
        return ResponseEntity.ok("Ваш запрос был успешно отправлен!");
    }

    @PostMapping("/save/audioBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<String> saveAudioBook(Authentication authentication, @RequestBody AudioBookSaveRequest audioBook) {
        audioBookService.saveAudioBook(authentication, audioBook);
        return ResponseEntity.ok("Ваш запрос был успешно отправлен!");
    }

    @DeleteMapping("/delete/{bookId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VENDOR')")
    public ResponseEntity<String> deleteBook(Authentication authentication, @PathVariable Long bookId) {
        paperBookService.deleteBook(authentication, bookId);
        return ResponseEntity.ok("Книга успешно удалена!");
    }

    @PutMapping("/paperBookUpdate/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> updatePaperBook(Authentication authentication, @PathVariable Long bookId, @RequestBody PaperBookSaveRequest paperBook) {
        paperBookService.updateBook(authentication, bookId, paperBook);
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

    @PutMapping("/eBookUpdate/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> updateEBook(Authentication authentication, @PathVariable Long bookId, @RequestBody ElectronicBookSaveRequest eBook) {
        eBookService.updateBook(authentication, bookId, eBook);
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

    @PutMapping("/audioBookUpdate/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    public ResponseEntity<String> updateEBook(Authentication authentication, @PathVariable Long bookId, @RequestBody AudioBookSaveRequest audioBook) {
        audioBookService.updateBook(authentication, bookId, audioBook);
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

}
