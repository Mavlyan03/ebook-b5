package kg.eBook.ebookb5.util;

import kg.eBook.ebookb5.models.Person;
import kg.eBook.ebookb5.services.PersonDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            personDetailsService.loadUserByUsername(person.getEmail());
        } catch (UsernameNotFoundException e) {
            errors.rejectValue("username", "", "This email is already in use!");
        }
    }
}
