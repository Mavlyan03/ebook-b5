package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.AudioBookResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.BookInnerPageResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.ElectronicBookResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.PaperBookResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.SortBy;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

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
        Pageable pageable = PageRequest.of(page - 1, size);
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

    public BookInnerPageResponse findById(Long id) {
        Book book = bookRepository.findById(id).get();
        book.setEnabled(true);
        bookRepository.save(book);
        if (book.getPublishedDate().plusDays(10).isAfter(LocalDate.now())) {
            book.setNew(true);
        }
        book.setEnabled(true);
        switch (book.getBookType()) {
            case AUDIO_BOOK:
                return new AudioBookResponse(book);
            case ELECTRONIC_BOOK:
                return new ElectronicBookResponse(book);
            case PAPER_BOOK:
                return new PaperBookResponse(book);
            default:
                return null;
        }
    }
}


