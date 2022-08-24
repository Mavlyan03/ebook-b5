package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
import kg.eBook.ebookb5.dto.responses.AdminApplicationsResponse;
import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.dto.responses.SearchResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.BookInnerPageResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.SortBy;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@CrossOrigin
public class BookApi {
    private final BookService bookService;

    @GetMapping
    public List<BookResponse> getAllBooks(
            @RequestParam(required = false) List<Long> genres,
            @RequestParam(required = false) BookType bookType,
            @RequestParam(required = false) Integer priceFrom,
            @RequestParam(required = false) Integer priceTo,
            @RequestParam(required = false) List<Language> languages,
            @RequestParam(required = false, defaultValue = "all") String search,
            @RequestParam(required = false) SortBy sortBy,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "12") int size
    ) {
        return bookService.getAllBooks(
                genres,
                bookType,
                priceFrom,
                priceTo,
                languages,
                search,
                sortBy,
                page,
                size
        );
    }

    @GetMapping("/search")
    public List<SearchResponse> globalSearchBooks(
            @RequestParam(required = false, defaultValue = "all") String search) {
        return bookService.globalSearchBooks(search);
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'USER', 'ADMIN')")
    @Operation(summary = "find by book with book id", description = "find inside book page with id")
    public BookInnerPageResponse findById(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }
    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AdminApplicationsResponse> getApplications(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "8") int size
    ) {
        return bookService.getApplications(
                page,
                size
        );
    }


    @GetMapping("/applications/accept")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void acceptBooks(Long id) {
        bookService.acceptBooks(id);
    }

    @GetMapping("/applications/reject")
    public void rejectBooks(
            Long id,
            String cause) {

        bookService.rejectBooks(
                id,
                "Причина Вашего отклонения",
                cause);
    }






}
