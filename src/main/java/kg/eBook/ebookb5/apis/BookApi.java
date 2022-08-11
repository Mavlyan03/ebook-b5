package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.SortBy;
import kg.eBook.ebookb5.services.BookService;
import lombok.RequiredArgsConstructor;
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


}
