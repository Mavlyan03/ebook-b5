package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.models.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfRegister;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getFirstName();
        this.email = user.getEmail();
        this.dateOfRegister = user.getCreated();
    }
}
