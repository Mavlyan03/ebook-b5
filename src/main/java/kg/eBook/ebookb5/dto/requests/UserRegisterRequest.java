package kg.eBook.ebookb5.dto.requests;

import kg.eBook.ebookb5.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterRequest {

    private String firstName;
    private String email;
    private String password;
    private Role role;
}
