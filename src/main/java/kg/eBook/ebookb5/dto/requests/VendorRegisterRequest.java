package kg.eBook.ebookb5.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorRegisterRequest {

    private String firstName;
    private String lastName;

    @Email
    private String email;

    private String phoneNumber;
    private String password;

}
