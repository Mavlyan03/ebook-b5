package kg.eBook.ebookb5.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterRequest {

    private String firstName;
    private String email;
    private String password;

    public static void main(String[] args) {
        System.out.println("LocalTime.now() = " + LocalTime.now());
    }

}
