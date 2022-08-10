package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.dto.responses.books.AudioBookResponse;
import kg.eBook.ebookb5.dto.responses.books.BookResponseGeneral;
import kg.eBook.ebookb5.dto.responses.books.EbookResponse;
import kg.eBook.ebookb5.dto.responses.books.PaperBookResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.SortBy;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

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

    public List<? extends BookResponseGeneral> finbBookById(Long bookId) {
        Book bookById = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(
                "Книга с ID: " + bookId + " не найдена!"
        ));

        if(bookById.getBookType().equals(BookType.PAPER_BOOK))
            return Collections.singletonList(bookToPaperBookResponse(bookById));

        if(bookById.getBookType().equals(BookType.ELECTRONIC_BOOK))
            return Collections.singletonList(bookToEbookResponse(bookById));

        if(bookById.getBookType().equals(BookType.AUDIO_BOOK))
            return Collections.singletonList(bookToAudioBookResponse(bookById));

        return null;
    }

    private PaperBookResponse bookToPaperBookResponse(Book book) {
        return modelMapper.map(book, PaperBookResponse.class);
    }

    private EbookResponse bookToEbookResponse(Book book) {
        return modelMapper.map(book, EbookResponse.class);
    }

    private AudioBookResponse bookToAudioBookResponse(Book book) {
        return modelMapper.map(book, AudioBookResponse.class);
    }
}


