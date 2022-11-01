package kg.eBook.ebookb5.dto.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class VendorProfileRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Email
    private String email;

    private String password;
    private String newPassword;

}
