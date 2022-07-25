package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.requests.LoginRequest;
import kg.eBook.ebookb5.dto.requests.UserRegisterRequest;
import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
import kg.eBook.ebookb5.dto.responses.JwtResponse;
import kg.eBook.ebookb5.security.JWT.JWTUtil;
import kg.eBook.ebookb5.services.LoginService;
import kg.eBook.ebookb5.services.UserService;
import kg.eBook.ebookb5.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthApi {

    private final JWTUtil jwtUtil;
    private final VendorService vendorService;
    private final UserService personService;
    private final LoginService loginService;

    @PostMapping("/vendor/register")
    public JwtResponse registrationVendor(@RequestBody VendorRegisterRequest vendorRegisterRequest) {
        return vendorService.registerVendor(vendorRegisterRequest);
    }

    @PostMapping("/user/register")
    public JwtResponse registrationPerson(@RequestBody UserRegisterRequest personRequest) {
        return personService.registerUser(personRequest);
    }

    @PostMapping("/login")
    public JwtResponse performLogin(@RequestBody LoginRequest loginResponse) {
       return loginService.authenticate(loginResponse);
    }
}


