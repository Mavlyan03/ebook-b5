package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.dto.responses.SearchResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.SortBy;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static kg.eBook.ebookb5.enums.SearchType.*;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    public List<BookResponse> getAllBooks(
                                          List<Long> genres,
                                          BookType bookType,
                                          Integer priceFrom,
                                          Integer priceTo,
                                          List<Language> languages,
                                          String search,
                                          SortBy sortBy,
                                          int page,
                                          int size
                                          ) {
    Pageable pageable = PageRequest.of(page-1, size);
        return bookRepository.customFindAll(
                genres,
                bookType,
                priceFrom,
                priceTo,
                languages,
                search,
                sortBy == null ? "all" : sortBy.getValue(),
                pageable
        );
    }
    public List<SearchResponse> globalSearchBooks(String search) {
        List<SearchResponse> all = new ArrayList<>();

        String finalSearch = search.toLowerCase();

        bookRepository.findAll().forEach(book -> {
            System.out.println(book);
            if (book.getName().toLowerCase().startsWith(finalSearch)) {
                all.add(new SearchResponse(book.getId(), book.getName(), BOOK));
            }
            if (book.getAuthor().toLowerCase().startsWith(finalSearch)) {
                all.add(new SearchResponse(book.getId(), book.getAuthor(), AUTHOR));
            }
            if ((!book.getBookType().equals(BookType.AUDIO_BOOK)) && book.getPublishingHouse().toLowerCase().startsWith(finalSearch)) {
                all.add(new SearchResponse(book.getId(), book.getPublishingHouse(), BOOK));
            }
        });

        genreRepository.findAll().forEach(genre -> {
            if (genre.getName().toLowerCase().startsWith(finalSearch)) {
                all.add(new kg.eBook.ebookb5.dto.responses.SearchResponse(genre.getId(), genre.getName(), GENRE));
            }
        });
        return all;
    }
}




