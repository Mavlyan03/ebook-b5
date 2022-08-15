package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.AdminApplicationsResponse;
import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.enums.SortBy;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    private final JavaMailSender mailSender;

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

    public List<AdminApplicationsResponse> getApplications(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return bookRepository.getApplications(pageable);
    }


    public void acceptBooks(Long id) {
        bookRepository.acceptBooks(id);
    }

    public void rejectBooks(
            Long id, String subject,
            String cause) {
        SimpleMailMessage message = new SimpleMailMessage();
        Book book = new Book();
        message.setFrom("timur.abdivaitov@gmail.com");
        message.setTo(book.getOwner().getEmail());
        message.setText(cause);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail sent successfully");

        bookRepository.rejectBooks(
                book.getId(),
                cause

        );
    }
}


