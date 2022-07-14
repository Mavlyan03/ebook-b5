package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.AuthDTO;
import kg.eBook.ebookb5.dto.mapper.PersonMapper;
import kg.eBook.ebookb5.dto.mapper.VendorMapper;
import kg.eBook.ebookb5.dto.requests.PersonRegisterRequest;
import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
import kg.eBook.ebookb5.models.Person;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import kg.eBook.ebookb5.services.PersonRegistrationService;
import kg.eBook.ebookb5.util.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApi {

    private final PersonRegistrationService personRegistrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;

    private final VendorMapper vendorMapper;
    private final PersonMapper personMapper;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/vendor/register")
    @PreAuthorize("hasAuthority('ROLE_VENDOR')")
    public Map<String, String> registration(@RequestBody @Valid VendorRegisterRequest vendorRegisterRequest, BindingResult bindingResult) {

        Person vendor = vendorMapper.DtoToVendor(vendorRegisterRequest);

        personValidator.validate(vendor, bindingResult);

        if(bindingResult.hasErrors())
            return Map.of("message", "Error!");

        personRegistrationService.registerVendor(vendor);

        String token = jwtUtil.generateToken(vendor.getEmail());

        return Map.of("jwt-token", token);
    }

    @PostMapping("/person/register")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Map<String, String> registration(@RequestBody @Valid PersonRegisterRequest personRequest, BindingResult bindingResult) {

        Person person = personMapper.DtoToPerson(personRequest);

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return Map.of("message", "Error!");

        personRegistrationService.registerPerson(person);

        String token = jwtUtil.generateToken(person.getEmail());

        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    @PreAuthorize("hasAnyAuthority({'ROLE_VENDOR', 'ROLE_USER', 'ROLE_ADMIN'})")
    public Map<String, String> performLogin(@RequestBody AuthDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getEmail(),
                        authenticationDTO.getPassword()
                );

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getEmail());
        return Map.of("jwt-token", token);
    }

}
