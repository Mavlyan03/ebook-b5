package kg.eBook.ebookb5.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorRegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

}
