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

    public JwtResponse registerUser(UserRegisterRequest userRegisterRequest) {

        User person = new User(
                userRegisterRequest.getFirstName(),
                userRegisterRequest.getEmail()
        );

        person.setRole(Role.USER);
        person.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));

        if(personRepository.existsByEmail(userRegisterRequest.getEmail()))
            throw new AlreadyExistException("The email " + userRegisterRequest.getEmail() + " is already in use!");

        User savedPerson = personRepository.save(person);
        String token = jwtUtil.generateToken(userRegisterRequest.getEmail());

        return new JwtResponse(
                savedPerson.getId(),
                token,
                savedPerson.getRole()
        );
    }


}
