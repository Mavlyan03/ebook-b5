package kg.eBook.ebookb5.models;

import kg.eBook.ebookb5.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    @Email
    private String email;

    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;
    private String password;

    @OneToMany
    private List<Book> book = new ArrayList();

    @OneToMany
    private Role role;

    @OneToMany
    private List<PromoCode> promoCodes = new ArrayList<>();

    private List<Book> basket = new ArrayList<>();

    private List<Book> favorite = new ArrayList<>();

}
