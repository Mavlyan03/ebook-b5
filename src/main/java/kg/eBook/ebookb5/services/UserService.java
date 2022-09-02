package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.UserRegisterRequest;
import kg.eBook.ebookb5.dto.requests.UserRequest;
import kg.eBook.ebookb5.dto.responses.*;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.exceptions.WrongPasswordException;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.BookRepository;
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
import java.util.List;

import static kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse.viewUserBooks;
import static kg.eBook.ebookb5.dto.responses.UserResponse.view;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    public JwtResponse registerUser(UserRegisterRequest userRegisterRequest) {

        logger.info("User register ... ");
        User person = new User(
                userRegisterRequest.getFirstName(),
                userRegisterRequest.getEmail()
        );
        person.setCreatedAt(LocalDate.now());
        person.setRole(Role.USER);
        person.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));

        if(personRepository.existsByEmail(userRegisterRequest.getEmail())) {
            logger.error("This email = " + userRegisterRequest.getEmail() + " al ready");
            throw new AlreadyExistException("Эта почта " + userRegisterRequest.getEmail() + " уже занята!");
        }
        User savedPerson = personRepository.save(person);
        String token = jwtUtil.generateToken(userRegisterRequest.getEmail());
        logger.info("User successfully created!");
        return new JwtResponse(
                savedPerson.getId(),
                token,
                savedPerson.getRole(),
                savedPerson.getFirstName()
        );
    }

    public List<UserResponse> findAllUsers() {
        logger.info("Find all users");
        return view(personRepository.findAllUsers());
    }

    public UserResponse findById(Long userId) {
        logger.info("Find by user with id = " + userId);
        return new UserResponse(personRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найдено")
        ));
    }

    public SimpleResponse deleteByUserId(Long userId) {
        logger.info("Delete user ... ");
        User user = personRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найдено")
        );

        // detach table users_basket_books
        user.getUserBasket().forEach(x -> x.removeUserFromBasket(user));
        bookRepository.saveAll(user.getUserBasket());

        // detach table users_favorite_books
        user.getFavorite().forEach(x -> x.removeUserFromLikes(user));
        bookRepository.saveAll(user.getFavorite());

        personRepository.delete(user);
        logger.info("User successfully deleted");
        return new SimpleResponse("Пользователь успешно удален!");
    }

    public List<PurchasedUserBooksResponse> findAllUsersFavoriteBooks(Long userId) {
        logger.info("Find all users favorite books");
        User user = personRepository.findById(userId).get();
        return viewUserBooks(user.getFavorite());
    }

    public List<PurchasedUserBooksResponse> findAllUserBooksInBasket(Long userId) {
        logger.info("Find all users basked books");
        User user = personRepository.findById(userId).get();
        return viewUserBooks(user.getUserBasket());
    }

    public SimpleResponse updateByUser(Authentication authentication, UserRequest userRequest) {
        logger.info("Update user ... ");
        User user = personRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("Пользователь не найдено"));
        String password = passwordEncoder.encode(userRequest.getPassword());
        String newPassword = passwordEncoder.encode(userRequest.getNewPassword());
        if (!user.getPassword().equals(password)) {
            logger.error("Password entered incorrectly");
            throw new WrongPasswordException("Пароль введен неправильно");
        }
        if (!user.getFirstName().equals(userRequest.getName()) && userRequest.getName() != null) {
            user.setFirstName(userRequest.getName());
        }
        if (!user.getEmail().equals(userRequest.getEmail()) && userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (!password.equals(newPassword) && newPassword != null) {
            user.setPassword(newPassword);
        }
        personRepository.save(user);
        logger.info("User update successful. ");
        return new SimpleResponse("Пользователь успешно сохранен");
    }
}
