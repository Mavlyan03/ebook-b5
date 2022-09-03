package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.VendorProfileRequest;
import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.dto.responses.VendorResponse;
import kg.eBook.ebookb5.dto.responses.ABookVendorResponse;
import kg.eBook.ebookb5.enums.AboutBooks;
import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.exceptions.WrongPasswordException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.NotificationRepository;
import kg.eBook.ebookb5.repositories.PurchasedUserBooksRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static kg.eBook.ebookb5.dto.responses.VendorResponse.viewVendors;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorService {

    private final UserRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final PurchasedUserBooksRepository purchasedUserBooksRepository;
    private final NotificationRepository notificationRepository;
    private final JWTUtil jwtUtil;
    private final BookRepository bookRepository;

    public JwtResponse registerVendor(VendorRegisterRequest vendorRegisterRequest) {

        User vendor = new User(
                vendorRegisterRequest.getFirstName(),
                vendorRegisterRequest.getLastName(),
                vendorRegisterRequest.getPhoneNumber(),
                vendorRegisterRequest.getEmail()
        );
        vendor.setCreatedAt(LocalDate.now());
        vendor.setRole(Role.VENDOR);
        vendor.setPassword(passwordEncoder.encode(vendorRegisterRequest.getPassword()));

        if (personRepository.existsByEmail(vendorRegisterRequest.getEmail()))
            throw new AlreadyExistException("Почта: " + vendorRegisterRequest.getEmail() + " уже занята!");

        User savedVendor = personRepository.save(vendor);

        String token = jwtUtil.generateToken(vendorRegisterRequest.getEmail());

        return new JwtResponse(
                savedVendor.getId(),
                token,
                savedVendor.getRole(),
                savedVendor.getFirstName());
    }

    public VendorResponse update(Authentication authentication,
                                 VendorProfileRequest vendorProfileRequest) {
        User vendor = personRepository.findByEmail(authentication.getName()).get();
        String password = passwordEncoder.encode(vendorProfileRequest.getPassword());
        String newPassword = passwordEncoder.encode(vendorProfileRequest.getNewPassword());
        if (!password.equals(vendor.getPassword())) {
            throw new WrongPasswordException("Не правильный пароль");
        }
        if (!vendor.getFirstName().equals(vendorProfileRequest.getFirstName()) &&
                vendorProfileRequest.getFirstName() != null) {
            vendor.setFirstName(vendorProfileRequest.getFirstName());
        }
        if (!vendor.getLastName().equals(vendorProfileRequest.getLastName()) &&
                vendorProfileRequest.getLastName() != null) {
            vendor.setLastName(vendorProfileRequest.getLastName());
        }
        if (!vendor.getEmail().equals(vendorProfileRequest.getEmail()) &&
                vendorProfileRequest.getEmail() != null) {
            vendor.setEmail(vendorProfileRequest.getEmail());
        }
        if (!vendor.getPhoneNumber().equals(vendorProfileRequest.getPhoneNumber()) &&
                vendorProfileRequest.getPhoneNumber() != null) {
            vendor.setPhoneNumber(vendorProfileRequest.getPhoneNumber());
        }
        if (!password.equals(newPassword) && newPassword != null) {
            vendor.setPassword(newPassword);
        }
        personRepository.save(vendor);
        return new VendorResponse(vendor);
    }

    public VendorResponse findByVendor(Long vendorId) {
        return new VendorResponse(personRepository.findById(vendorId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найдено")));
    }

    public SimpleResponse deleteByVendorId(Long vendorId) {
        User vendor = personRepository.findById(vendorId).orElseThrow(
                () -> new NotFoundException("Пользователь не найдено"));

        personRepository.delete(vendor);
        return new SimpleResponse("Вы успешно удалили аккаунт");
    }

    public List<VendorResponse> findAllVendors() {
        return viewVendors(personRepository.findAllVendors());
    }

    public Page<ABookVendorResponse> findABookVendor(Long vendorId, AboutBooks aboutBooks, int page, int size) {

        User vendor = personRepository.findById(vendorId).orElseThrow(
                () -> new NotFoundException("Пользователь не найдено"));
        Pageable pageable = PageRequest.of(page - 1, size);
        switch (aboutBooks) {
            case ALL:
                List<ABookVendorResponse> bookVendorResponses = ABookVendorResponse.viewBooks(vendor.getBooks());
                return new PageImpl<>(bookVendorResponses.subList(start(pageable, bookVendorResponses),
                        end(pageable, bookVendorResponses)), pageable, bookVendorResponses.size());
            case REJECTED:
                return new PageImpl<>(booksRejected(vendor.getBooks()).subList(
                        start(pageable, booksRejected(vendor.getBooks())),
                        end(pageable, booksRejected(vendor.getBooks()))),
                        pageable, booksRejected(vendor.getBooks()).size());
            case IN_THE_PROCESS:
                return new PageImpl<>(booksInTheProcess(vendor.getBooks()).subList(
                        start(pageable, booksInTheProcess(vendor.getBooks())),
                        end(pageable, booksInTheProcess(vendor.getBooks()))),
                        pageable, booksInTheProcess(vendor.getBooks()).size());
            case WITH_DISCOUNTS:
                return new PageImpl<>(booksWithDiscounts(vendor.getBooks()).subList(
                        start(pageable, booksWithDiscounts(vendor.getBooks())),
                        end(pageable, booksWithDiscounts(vendor.getBooks()))),
                        pageable, booksWithDiscounts(vendor.getBooks()).size());
            case IN_THE_BASKET:
                return new PageImpl<>(booksInTheBasket(vendor.getBooks()).subList(
                        start(pageable, booksInTheBasket(vendor.getBooks())),
                        end(pageable, booksInTheBasket(vendor.getBooks()))),
                        pageable, booksInTheBasket(vendor.getBooks()).size());
            case FAVORITES:
                return new PageImpl<>(booksFavorites(vendor.getBooks()).subList(
                        start(pageable, booksFavorites(vendor.getBooks())),
                        end(pageable, booksFavorites(vendor.getBooks()))),
                        pageable, booksFavorites(vendor.getBooks()).size());
            case SOLD_OUT:
                return new PageImpl<>(booksSoldOut(vendor.getBooks()).subList(
                        start(pageable, booksSoldOut(vendor.getBooks())),
                        end(pageable, booksSoldOut(vendor.getBooks()))),
                        pageable, booksSoldOut(vendor.getBooks()).size());
            default:
                return null;
        }
    }

    private int start(Pageable pageable, List<ABookVendorResponse> bookVendorResponses) {
        return Math.min((int) pageable.getOffset(), bookVendorResponses.size());
    }

    private int end(Pageable pageable, List<ABookVendorResponse> bookVendorResponses) {
        return Math.min((start(pageable, bookVendorResponses) + pageable.getPageSize()),
                bookVendorResponses.size());
    }

    private List<ABookVendorResponse> booksRejected(List<Book> books) {
        List<ABookVendorResponse> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookStatus().equals(BookStatus.REJECTED)) {
                bookList.add(new ABookVendorResponse(book));
            }
        }
        return bookList;
    }

    private List<ABookVendorResponse> booksInTheProcess(List<Book> books) {
        List<ABookVendorResponse> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookStatus().equals(BookStatus.IN_PROCESSING)) {
                bookList.add(new ABookVendorResponse(book));
            }
        }
        return bookList;
    }

    private List<ABookVendorResponse> booksWithDiscounts(List<Book> books) {
        List<ABookVendorResponse> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getDiscount() > 0) {
                bookList.add(new ABookVendorResponse(book));
            }
        }
        return bookList;
    }

    private List<ABookVendorResponse> booksInTheBasket(List<Book> books) {
        List<ABookVendorResponse> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookBasket().size() > 0) {
                bookList.add(new ABookVendorResponse(book));
            }
        }
        return bookList;
    }

    private List<ABookVendorResponse> booksFavorites(List<Book> books) {
        List<ABookVendorResponse> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getLikes().size() > 0) {
                bookList.add(new ABookVendorResponse(book));
            }
        }
        return bookList;
    }

    private List<ABookVendorResponse> booksSoldOut(List<Book> books) {
        List<ABookVendorResponse> bookList = new ArrayList<>();
        for (Book book : books) {
            if (purchasedUserBooksRepository.existsPurchasedUserBooksByBookId(book.getId())) {
                bookList.add(new ABookVendorResponse(book));
            }
        }
        return bookList;
    }
}
