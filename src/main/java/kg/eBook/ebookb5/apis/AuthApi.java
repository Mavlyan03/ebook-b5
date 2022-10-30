package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.requests.LoginRequest;
import kg.eBook.ebookb5.dto.requests.UserRegisterRequest;
import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.exceptions.WrongEmailException;
import kg.eBook.ebookb5.services.LoginService;
import kg.eBook.ebookb5.services.UserService;
import kg.eBook.ebookb5.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Auth API", description = "Registration and authentication")
public class AuthApi {

    private final VendorService vendorService;
    private final UserService personService;
    private final LoginService loginService;

    @Operation(summary = "Vendor registration", description = "Registration as a vendor")
    @PostMapping("/vendor/register")
    public JwtResponse registrationVendor(@RequestBody @Valid VendorRegisterRequest vendorRegisterRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new WrongEmailException("Неправильный адрес электронной почты");
        }
        return vendorService.registerVendor(vendorRegisterRequest);
    }

    @Operation(summary = "User registration", description = "Registration as a client")
    @PostMapping("/user/register")
    public JwtResponse registrationPerson(@RequestBody @Valid UserRegisterRequest personRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new WrongEmailException("Неправильный адрес электронной почты");
        }
        return personService.registerUser(personRequest);
    }

    @Operation(summary = "Sign in", description = "Only registered users can login")
    @PostMapping("/login")
    public JwtResponse performLogin(@RequestBody LoginRequest loginResponse) {
       return loginService.authenticate(loginResponse);
    }
}


