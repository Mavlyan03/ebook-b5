package kg.eBook.ebookb5.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRegisterRequest {

    private String firstName;
    private String email;
    private String password;

}
