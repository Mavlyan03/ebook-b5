package kg.eBook.ebookb5.db.services;

import kg.eBook.ebookb5.db.models.User;
import kg.eBook.ebookb5.db.repositories.UserRepository;
import kg.eBook.ebookb5.configs.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository personRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> person = personRepository.findByEmail(email);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new UserDetailsImpl(person.get());
    }

}
