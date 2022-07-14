package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.models.Person;
import kg.eBook.ebookb5.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonRegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerPerson(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Role.ROLE_USER);
        personRepository.save(person);
    }

    public void registerVendor(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Role.ROLE_VENDOR);
        personRepository.save(person);
    }

}
