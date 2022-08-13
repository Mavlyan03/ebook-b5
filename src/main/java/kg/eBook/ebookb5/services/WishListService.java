package kg.eBook.ebookb5.services;


import kg.eBook.ebookb5.dto.responses.books.AudioBookResponse;
import kg.eBook.ebookb5.dto.responses.books.BookResponseGeneral;
import kg.eBook.ebookb5.dto.responses.books.EbookResponse;
import kg.eBook.ebookb5.dto.responses.books.PaperBookResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WishListService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void addBookToWishList(Long bookId, Authentication authentication) {

        User user1 = userRepository.findByEmail(authentication.getName()).get();

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("Книга с ID: " + bookId + "не найдена"
        ));

        user1.setFavoriteBook(book);
        book.setUserToBook(user1);
    }

    public List<? extends BookResponseGeneral> getBooks(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).get();

        List<Book> userFavorite = user.getFavorite();
        List<PaperBookResponse> paperBookResponseList = new ArrayList<>();
        List<EbookResponse> ebookResponseList = new ArrayList<>();
        List<AudioBookResponse> audioBookResponseList = new ArrayList<>();

        for(Book i: userFavorite) {
            if (i.getBookType().equals(BookType.PAPER_BOOK)) {
                paperBookResponseList.add(paperBookToResponse(i));
                return paperBookResponseList;
            }
            else if (i.getBookType().equals(BookType.AUDIO_BOOK)) {
                audioBookResponseList.add(audioBookToResponse(i));
                return audioBookResponseList;
            }
            else if (i.getBookType().equals(BookType.ELECTRONIC_BOOK)) {
                ebookResponseList.add(ebookToResponse(i));
                return ebookResponseList;
            }
        }
        return null;
    }

    private PaperBookResponse paperBookToResponse(Book book) {
        return modelMapper.map(book, PaperBookResponse.class);
    }

    private AudioBookResponse audioBookToResponse(Book book) {
        return modelMapper.map(book, AudioBookResponse.class);
    }

    private EbookResponse ebookToResponse(Book book) {
        return modelMapper.map(book, EbookResponse.class);
    }
}
