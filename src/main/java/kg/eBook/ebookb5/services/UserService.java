package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.UserRegisterRequest;
import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.UserRepository;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public JwtResponse registerPerson(UserRegisterRequest personRegisterRequest) {

        User person = new User(
                personRegisterRequest.getFirstName(),
                personRegisterRequest.getEmail()
        );

        person.setRole(Role.USER);
        person.setPassword(passwordEncoder.encode(personRegisterRequest.getPassword()));

        if(personRepository.findByEmail(personRegisterRequest.getEmail()).orElse(null) != null)
            throw new AlreadyExistException("The email " + personRegisterRequest.getEmail() + " is already in use!");

        User savedPerson = personRepository.save(person);
        String token = jwtUtil.generateToken(personRegisterRequest.getEmail());

        return new JwtResponse(
                savedPerson.getId(),
                token,
                savedPerson.getRole()
        );
    }

    public User findByEmail(String email) {
        return personRepository.findByEmail(email).orElseThrow();
    }

    public User findById(Long id) {
        return personRepository.findById(id).orElseThrow(NotFoundException::new);
    }

}
