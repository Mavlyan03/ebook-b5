package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.dto.responses.SearchResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.BookInnerPageResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.SortBy;
import kg.eBook.ebookb5.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@CrossOrigin
public class BookApi {
    private final BookService bookService;

    @GetMapping
    public Page<BookResponse> getAllBooks(
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
    @Operation(summary = "find by book with book id", description = "find inside book page with id")
    public BookInnerPageResponse findById(@PathVariable Long bookId, Authentication authentication) {
        return bookService.findById(bookId, authentication);
    }
}