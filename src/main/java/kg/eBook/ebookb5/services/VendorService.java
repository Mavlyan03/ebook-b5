package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
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
public class VendorService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    public JwtResponse registerVendor(VendorRegisterRequest vendorRegisterRequest) {

        Person vendor = new Person(
                vendorRegisterRequest.getFirstName(),
                vendorRegisterRequest.getLastName(),
                vendorRegisterRequest.getPhoneNumber(),
                vendorRegisterRequest.getEmail()
        );

        vendor.setRole(Role.VENDOR);
        vendor.setPassword(passwordEncoder.encode(vendorRegisterRequest.getPassword()));

        if(personRepository.findByEmail(vendorRegisterRequest.getEmail()).orElse(null) != null)
            throw new EmailIsAlreadyExistException("The email " + vendorRegisterRequest.getEmail() + " is already in use!");

        Person savedVendor = personRepository.save(vendor);

        String token = jwtUtil.generateToken(vendorRegisterRequest.getEmail());

        return new JwtResponse(
                savedVendor.getId(),
                token,
                savedVendor.getRole());
    }

}
