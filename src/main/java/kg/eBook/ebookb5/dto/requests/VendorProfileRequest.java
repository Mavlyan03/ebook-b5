package kg.eBook.ebookb5.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorProfileRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String newPassword;
}
