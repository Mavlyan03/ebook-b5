package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.db.models.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        this.dateOfRegister = user.getCreatedAt();
    }

    public static List<UserResponse> view(List<User> users) {

        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : users) {
            userResponses.add(new UserResponse(user));
        }
        return userResponses;
    }
}
