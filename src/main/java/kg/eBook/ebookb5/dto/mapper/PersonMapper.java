package kg.eBook.ebookb5.dto.mapper;

import kg.eBook.ebookb5.dto.requests.PersonRegisterRequest;
import kg.eBook.ebookb5.dto.responses.PersonRegisterResponse;
import kg.eBook.ebookb5.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    public PersonRegisterResponse PersonToDto(Person person) {
        PersonRegisterResponse personResponse = new PersonRegisterResponse();
        personResponse.setId(person.getId());
        personResponse.setFirstName(person.getFirstName());
        personResponse.setLastName(person.getLastName());
        personResponse.setEmail(person.getEmail());
        personResponse.setPhoneNumber(person.getPhoneNumber());
        personResponse.setPassword(person.getPassword());
        return personResponse;
    }

    public Person DtoToPerson(PersonRegisterRequest personRequest) {
        Person person = new Person();
        person.setFirstName(personRequest.getFirstName());
        person.setEmail(personRequest.getEmail());
        person.setPassword(personRequest.getPassword());
        return person;
    }


}
