package kg.eBook.ebookb5.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorRegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

}
