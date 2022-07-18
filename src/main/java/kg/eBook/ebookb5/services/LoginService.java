package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.dto.requests.LoginRequest;
import kg.eBook.ebookb5.models.Person;
import kg.eBook.ebookb5.repositories.PersonRepository;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JWTUtil jwtUtil;
    private final PersonRepository personRepository;


    public JwtResponse loginMethod(LoginRequest loginRequest) {

        Person user = personRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "user with email = " + loginRequest.getEmail() + " not found!"
                ));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new BadCredentialsException(
                    "invalid password"
            );
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new JwtResponse(
                user.getId(),
                token,
                user.getRole()
        );
    }
}
