package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.models.Person;
import kg.eBook.ebookb5.repositories.PersonRepository;
import kg.eBook.ebookb5.security.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByEmail(email);
        if(person.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return new PersonDetails(person.get());
    }
}
