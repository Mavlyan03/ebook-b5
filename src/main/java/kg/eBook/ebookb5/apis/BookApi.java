package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.dto.responses.SearchResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.BookInnerPageResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.SortBy;
import kg.eBook.ebookb5.db.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public/books")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Public Book API", description = "Public book endpoints")
public class BookApi {

    private final BookService bookService;

    @Operation(summary = "Get all books", description = "Public get all books page")
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
            @RequestParam(required = false, defaultValue = "12") int size) {
        return bookService.getAllBooks(genres, bookType, priceFrom, priceTo, languages, search, sortBy, page, size);
    }

    @Operation(summary = "Global search", description = "Search books")
    @GetMapping("/search")
    public List<SearchResponse> globalSearchBooks(@RequestParam(required = false, defaultValue = "all") String search) {
        return bookService.globalSearchBooks(search);
    }

    @Operation(summary = "Get book by id", description = "Get book inner page by id")
    @GetMapping("/{bookId}")
    public BookInnerPageResponse findById(@PathVariable Long bookId, Authentication authentication) {
        return bookService.findById(bookId, authentication);
    }

}