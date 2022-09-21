package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.*;
import kg.eBook.ebookb5.dto.responses.books.ABookResponse;
import kg.eBook.ebookb5.dto.responses.books.BookResponseGeneral;
import kg.eBook.ebookb5.dto.responses.books.EbookResponse;
import kg.eBook.ebookb5.dto.responses.books.PBookResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.AudioBookResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.BookInnerPageResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.ElectronicBookResponse;
import kg.eBook.ebookb5.dto.responses.findByBookId.PaperBookResponse;
import kg.eBook.ebookb5.dto.responses.userMainPage.MainPageResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.enums.SortBy;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.GenreRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static kg.eBook.ebookb5.enums.SearchType.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;
    private final UserRepository personRepository;

    public Page<BookResponse> getAllBooks(
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

    public Page<AdminApplicationsResponse> getApplications(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return bookRepository.getApplications(pageable);
    }

    public ApplicationResponse applications(int page, int size) {

        ApplicationResponse applicationResponse = new ApplicationResponse(
                bookRepository.countOfUnseen(),
                getApplications(page, size)
        );
        return applicationResponse;
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
                all.add(new SearchResponse(genre.getId(), genre.getName(), GENRE));
            }
        });
        return all;
    }

    public List<? extends BookResponseGeneral> findBookById(Long bookId) {

        Book bookById = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(
                "Книга с ID: " + bookId + " не найдена!"
        ));

        if (bookById.getBookType().equals(BookType.PAPER_BOOK)) {
            return Collections.singletonList(bookToPaperBookResponse(bookById));
        }
        if (bookById.getBookType().equals(BookType.ELECTRONIC_BOOK)) {
            return Collections.singletonList(bookToEbookResponse(bookById));
        }
        if (bookById.getBookType().equals(BookType.AUDIO_BOOK)) {
            return Collections.singletonList(bookToAudioBookResponse(bookById));
        }
        return null;
    }

    public BookInnerPageResponse findById(Long id, Authentication authentication) {

        Book book = bookRepository.findById(id).get();

        if (authentication != null) {
            User user = personRepository.findByEmail(authentication.getName()).get();
            if (user.getRole().equals(Role.ADMIN)) {
                book.setEnabled(true);
            }
        }
        bookRepository.save(book);
        if (book.isNew()) {
            if (!book.getPublishedDate().plusDays(10).isAfter(LocalDate.now())) {
                book.setNew(false);
            }
        }
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

    private PBookResponse bookToPaperBookResponse(Book book) {
        return modelMapper.map(book, PBookResponse.class);
    }

    private EbookResponse bookToEbookResponse(Book book) {
        return modelMapper.map(book, EbookResponse.class);
    }

    private ABookResponse bookToAudioBookResponse(Book book) {
        return modelMapper.map(book, ABookResponse.class);
    }

    public Page<AdminBooksResponse> findAllBooks(Long genreId,
                                                 BookType bookType,
                                                 int page,
                                                 int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return bookRepository.findAllBooks(genreId,
                bookType,
                pageable);
    }

    public MainPageResponse mainPageResponse() {

        return new MainPageResponse(
                bookRepository.findAllFavoriteBooks(PageRequest.of(0, 4)),
                bookRepository.findAllBestsellerBooks(PageRequest.of(0, 5)),
                bookRepository.findAllLastPublicationsBooks(PageRequest.of(0, 6)),
                bookRepository.findAllFavoriteAudioBooks(PageRequest.of(0, 3)),
                bookRepository.findAllFavoriteElectronicBooks(PageRequest.of(0, 5)));
    }
}