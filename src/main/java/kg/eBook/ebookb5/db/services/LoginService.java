package kg.eBook.ebookb5.db.services;

import kg.eBook.ebookb5.dto.requests.LoginRequest;
import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.exceptions.WrongPasswordException;
import kg.eBook.ebookb5.db.models.User;
import kg.eBook.ebookb5.db.repositories.UserRepository;
import kg.eBook.ebookb5.configs.security.JWT.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final JWTUtil jwtUtil;
    private final UserRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse authenticate(LoginRequest loginRequest) {
        User user = personRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException(
                        "Пользователь с почтой " + loginRequest.getEmail() + " не найден"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("Неверный пароль");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new JwtResponse(
                user.getId(),
                token,
                user.getRole(),
                user.getFirstName()
        );
    }

}
