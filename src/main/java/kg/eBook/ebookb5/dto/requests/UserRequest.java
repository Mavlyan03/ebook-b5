package kg.eBook.ebookb5.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private String newPassword;

}
