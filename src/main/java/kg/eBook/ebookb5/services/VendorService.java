package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.VendorProfileRequest;
import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
import kg.eBook.ebookb5.dto.responses.ABookVendorResponse;
import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.dto.responses.VendorResponse;
import kg.eBook.ebookb5.enums.AboutBooks;
import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.exceptions.WrongPasswordException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.Notification;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.NotificationRepository;
import kg.eBook.ebookb5.repositories.PurchasedUserBooksRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static kg.eBook.ebookb5.dto.responses.ABookVendorResponse.viewBooks;
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
    private final Logger logger = LoggerFactory.getLogger(VendorService.class);

    public JwtResponse registerVendor(VendorRegisterRequest vendorRegisterRequest) {

        logger.info("Vendor register ... ");
        User vendor = new User(
                vendorRegisterRequest.getFirstName(),
                vendorRegisterRequest.getLastName(),
                vendorRegisterRequest.getPhoneNumber(),
                vendorRegisterRequest.getEmail()
        );
        vendor.setCreatedAt(LocalDate.now());
        vendor.setRole(Role.VENDOR);
        vendor.setPassword(passwordEncoder.encode(vendorRegisterRequest.getPassword()));

        if (personRepository.existsByEmail(vendorRegisterRequest.getEmail())) {

            logger.error("This email = {} al ready", vendorRegisterRequest.getEmail());
            throw new AlreadyExistException("Почта: " + vendorRegisterRequest.getEmail() + " уже занята!");
        }
        User savedVendor = personRepository.save(vendor);

        String token = jwtUtil.generateToken(vendorRegisterRequest.getEmail());

        logger.info("User successfully created!");
        return new JwtResponse(
                savedVendor.getId(),
                token,
                savedVendor.getRole(),
                savedVendor.getFirstName());
    }

    public VendorResponse update(Authentication authentication,
                                 VendorProfileRequest vendorProfileRequest) {

        logger.info("Update user ... ");
        User vendor = personRepository.findByEmail(authentication.getName()).get();

        if (!passwordEncoder.matches(vendorProfileRequest.getPassword(), vendor.getPassword())) {
            throw new WrongPasswordException("Неверный пароль");
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
        if (!passwordEncoder.matches(vendorProfileRequest.getNewPassword(), vendor.getPassword())
                && !vendorProfileRequest.getNewPassword().equals("")) {
            vendor.setPassword(passwordEncoder.encode(vendorProfileRequest.getNewPassword()));
        }
        personRepository.save(vendor);

        logger.info("User update successful");
        return new VendorResponse(vendor);
    }

    public VendorResponse findByVendor(Long vendorId) {

        logger.info("Find by vendor with id = " + vendorId);
        return new VendorResponse(personRepository.findById(vendorId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найдено")));
    }

    public SimpleResponse deleteByVendorId(Long vendorId) {

        logger.info("Delete vendor ...");
        User vendor = personRepository.findById(vendorId).orElseThrow(
                () -> new NotFoundException("Пользователь не найдено"));

        personRepository.delete(vendor);
        logger.info("Vendor successfully deleted");
        return new SimpleResponse("Вы успешно удалили аккаунт");
    }

    public List<VendorResponse> findAllVendors() {

        logger.info("Find all vendors favorite books");
        return viewVendors(personRepository.findAllVendors());
    }

    public List<ABookVendorResponse> findABookVendor(Long vendorId, AboutBooks aboutBooks) {

        logger.info("Find books vendor ...");
        User vendor = personRepository.findById(vendorId).orElseThrow(
                () -> new NotFoundException("Пользователь не найдено"));

        switch (aboutBooks) {
            case ALL:
                return viewBooks(vendor.getBooks());
            case REJECTED:
                logger.info("Books rejected");
                return viewBooks(booksRejected(vendor.getBooks()));
            case IN_THE_PROCESS:
                logger.info("Books in the process");
                return viewBooks(booksInTheProcess(vendor.getBooks()));
            case WITH_DISCOUNTS:
                logger.info("Books with discounts");
                return viewBooks(booksWithDiscounts(vendor.getBooks()));
            case IN_THE_BASKET:
                logger.info("Books in the basked");
                return viewBooks(booksInTheBasket(vendor.getBooks()));
            case FAVORITES:
                logger.info("Books favorite");
                return viewBooks(booksFavorites(vendor.getBooks()));
            case SOLD_OUT:
                logger.info("Books sold out");
                return viewBooks(booksSoldOut(vendor.getBooks()));
            default:
                return null;
        }
    }

    private List<Book> booksRejected(List<Book> books) {
        List<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookStatus().equals(BookStatus.REJECTED)) {
                Notification notification = notificationRepository.findByBookId(book.getId()).orElseThrow(
                        () -> new NotFoundException("Уведомление не найдено"));
                book.setPublishedDate(notification.getCreatedAt());
                bookList.add(book);
            }
        }
        return bookList;
    }

    private List<Book> booksInTheProcess(List<Book> books) {
        List<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookStatus().equals(BookStatus.IN_PROCESSING)) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    private List<Book> booksWithDiscounts(List<Book> books) {
        List<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getDiscount() > 0) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    private List<Book> booksInTheBasket(List<Book> books) {
        List<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookBasket().size() > 0) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    private List<Book> booksFavorites(List<Book> books) {
        List<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            if (book.getLikes().size() > 0) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    private List<Book> booksSoldOut(List<Book> books) {
        List<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            if (purchasedUserBooksRepository.existsPurchasedUserBooksByBookId(book.getId())) {
                bookList.add(book);
            }
        }
        return bookList;
    }
}
