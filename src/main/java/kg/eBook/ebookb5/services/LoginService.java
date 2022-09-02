package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.dto.requests.LoginRequest;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.exceptions.WrongPasswordException;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.UserRepository;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final JWTUtil jwtUtil;
    private final UserRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public JwtResponse authenticate(LoginRequest loginRequest) {

        logger.info("Authenticate user ...");
        User user = personRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException(
                        "Пользователь с почтой " + loginRequest.getEmail() + " не найден"
                ));


        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

            logger.error("User with email {} entered the wrong password", loginRequest.getEmail());
            throw new WrongPasswordException(
                    "Неверный пароль"
            );
        }

        String token = jwtUtil.generateToken(user.getEmail());

        logger.info("User successfully authenticated");
        return new JwtResponse(
                user.getId(),
                token,
                user.getRole(),
                user.getFirstName()
        );
    }
}
