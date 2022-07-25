package kg.eBook.ebookb5.models;

import kg.eBook.ebookb5.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Book> books = new ArrayList();

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "vendor")
    private List<PromoCode> promoCodes = new ArrayList<>();

    @ManyToMany(mappedBy = "basket")
    private List<Book> basket = new ArrayList<>();

    @ManyToMany(mappedBy = "likes")
    private List<Book> favorite = new ArrayList<>();

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

}

