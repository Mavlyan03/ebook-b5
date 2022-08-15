package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.VendorProfileRequest;
import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.dto.responses.VendorResponse;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.exceptions.WrongPasswordException;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorService {

    private final UserRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    private final BookRepository bookRepository;

    public JwtResponse registerVendor(VendorRegisterRequest vendorRegisterRequest) {

        User vendor = new User(
                vendorRegisterRequest.getFirstName(),
                vendorRegisterRequest.getLastName(),
                vendorRegisterRequest.getPhoneNumber(),
                vendorRegisterRequest.getEmail()
        );

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
        if (password.equals(vendor.getPassword())) {
            throw new WrongPasswordException("Не правильный паспорт");
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
        if (vendorProfileRequest.getNewPassword() != null &&
                !vendorProfileRequest.getPassword().equals(vendorProfileRequest.getNewPassword())) {
            vendor.setPassword(passwordEncoder.encode(vendorProfileRequest.getNewPassword()));
        }
        personRepository.save(vendor);
        return new VendorResponse(vendor);
    }

    public VendorResponse findByVendor(Authentication authentication) {
        return new VendorResponse(personRepository.findByEmail(authentication.getName()).get());
    }

    public SimpleResponse deleteByVendorId(Long vendorId) {
        User vendor = personRepository.findById(vendorId).get();
        for (Book book : vendor.getBooks()) {
            book.deleteBookBasket();
            book.deleteBookFavorite();
            bookRepository.save(book);
        }
        personRepository.delete(vendor);
        return new SimpleResponse("Вы успешно удалили аккаунт");
    }

//    public SimpleResponse deleteByUserId(Long userId) {
//        User user = personRepository.findById(userId).get();
//
//        // detach table users_basket_books
//        user.getUserBasket().forEach(x -> x.removeUserFromBasket(user));
//        bookRepository.saveAll(user.getUserBasket());
//
//        // detach table users_favorite_books
//        user.getFavorite().forEach(x -> x.removeUserFromLikes(user));
//        bookRepository.saveAll(user.getFavorite());
//
//        personRepository.delete(user);
//        return new SimpleResponse("Пользователь успешно удален!");
//    }

}
