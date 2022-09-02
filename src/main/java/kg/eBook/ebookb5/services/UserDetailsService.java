package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.repositories.UserRepository;
import kg.eBook.ebookb5.security.UserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository personRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> person = personRepository.findByEmail(email);
        if(person.isEmpty()) {
            logger.error("Not found user with = " + email);
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        logger.info("Found user = " + person);
        return new UserDetails(person.get());
    }
}
