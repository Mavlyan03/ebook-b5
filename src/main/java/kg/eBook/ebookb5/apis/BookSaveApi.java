package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.requests.books.AudioBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.ElectronicBookSaveRequest;
import kg.eBook.ebookb5.dto.requests.books.PaperBookSaveRequest;
import kg.eBook.ebookb5.services.book.AudioBookService;
import kg.eBook.ebookb5.services.book.ElectronicBookService;
import kg.eBook.ebookb5.services.book.PaperBookService;
import lombok.RequiredArgsConstructor;
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

    //TODO ADD CLASS INSTEAD STRING
    @PostMapping("/save/paperBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public String savePaperBook(Authentication authentication, @RequestBody PaperBookSaveRequest paperBook) {
        paperBookService.savePaperBook(authentication, paperBook, PAPER_BOOK);
        return "Ваш запрос был успешно отправлен!";
    }

    //TODO ADD CLASS INSTEAD STRING
    @PostMapping("/save/eBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public String saveEbook(Authentication authentication, @RequestBody ElectronicBookSaveRequest eBook) {
        eBookService.saveElectronicBook(authentication, eBook, ELECTRONIC_BOOK);
        return "Ваш запрос был успешно отправлен!";
    }

    //TODO ADD CLASS INSTEAD STRING
    @PostMapping("/save/audioBook")
    @PreAuthorize("hasAuthority('VENDOR')")
    public String saveAudioBook(Authentication authentication, @RequestBody AudioBookSaveRequest audioBook) {
        audioBookService.saveAudioBook(authentication, audioBook, AUDIO_BOOK);
        return "Ваш запрос был успешно отправлен!";
    }

}
