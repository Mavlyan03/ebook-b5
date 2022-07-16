package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.PersonRegisterRequest;
import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.exceptions.EmailIsAlreadyExistException;
import kg.eBook.ebookb5.models.Person;
import kg.eBook.ebookb5.repositories.PersonRepository;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public JwtResponse registerPerson(PersonRegisterRequest personRegisterRequest) {

        Person person = new Person(
                personRegisterRequest.getFirstName(),
                personRegisterRequest.getEmail()
        );

        person.setRole(Role.USER);
        person.setPassword(passwordEncoder.encode(personRegisterRequest.getPassword()));

        if(personRepository.findByEmail(personRegisterRequest.getEmail()).orElse(null) != null)
            throw new EmailIsAlreadyExistException("The email " + personRegisterRequest.getEmail() + " is already in use!");

        Person savedPerson = personRepository.save(person);
        String token = jwtUtil.generateToken(personRegisterRequest.getEmail());

        return new JwtResponse(
                savedPerson.getId(),
                token,
                savedPerson.getRole()
        );
    }

}
