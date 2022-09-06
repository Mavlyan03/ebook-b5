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

        return switch (aboutBooks) {
            case ALL -> bookRepository.findBooksByOwnerId(vendorId, pageable);
            case REJECTED, IN_PROCESSING ->
                    bookRepository.findBooksByOwnerIdAndBookStatus(vendorId, BookStatus.valueOf(aboutBooks.name()), pageable);
            case WITH_DISCOUNTS -> bookRepository.findBooksByOwnerIdAndDiscountNotNull(vendorId, pageable);
            case IN_THE_BASKET -> bookRepository.findBooksByOwnerIdAndBookBasketIsNotNull(vendorId, pageable);
            case FAVORITES -> bookRepository.findBooksByOwnerIdAndLikesIsNotNull(vendorId, pageable);
            case SOLD_OUT -> bookRepository.findAllById(booksSoldOut(vendor.getBooks()), pageable);
        };
    }

    private List<Long> booksSoldOut(List<Book> books) {
        List<Long> bookIds = new ArrayList<>();
        for (Book book : books) {
            if (purchasedUserBooksRepository.existsPurchasedUserBooksByBookId(book.getId())) {
                bookIds.add(book.getId());
            }
        }
        return bookIds;
    }
}
