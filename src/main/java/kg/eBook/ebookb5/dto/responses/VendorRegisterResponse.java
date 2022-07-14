package kg.eBook.ebookb5.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorRegisterResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

}
