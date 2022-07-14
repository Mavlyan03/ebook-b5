package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.models.Person;
import kg.eBook.ebookb5.repositories.PersonRepository;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    private final PersonRepository personRepository;

    private final JWTUtil jwtUtil;

    public VendorService(PersonRepository personRepository,
                         JWTUtil jwtUtil) {
        this.personRepository = personRepository;
        this.jwtUtil = jwtUtil;
    }

    public JwtResponse register(VendorRegisterRequest vendorRegisterRequest) {

        Person vendor = new Person(vendorRegisterRequest);

        vendor.setRole(Role.ROLE_VENDOR);

        // check email
        // ...

        Person savedPerson = personRepository.save(vendor);

        return new JwtResponse(
                savedPerson.getId(),
                jwtUtil.generateToken(savedPerson.getEmail()),
                savedPerson.getRole()
        );
    }
}
