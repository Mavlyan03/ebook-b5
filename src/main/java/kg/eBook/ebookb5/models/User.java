package kg.eBook.ebookb5.models;

import kg.eBook.ebookb5.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class User {

    public User(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String firstName, String email) {
        this.firstName = firstName;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
    private String password;

//    @OneToMany
//    private List<Book> book = new ArrayList();

    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany
//    private List<PromoCode> promoCodes = new ArrayList<>();
//
//    private List<Book> basket = new ArrayList<>();
//
//    private List<Book> favorite = new ArrayList<>();

}
