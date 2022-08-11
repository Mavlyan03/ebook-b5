package kg.eBook.ebookb5.models;

import kg.eBook.ebookb5.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "users")
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

    @CreatedDate
    private LocalDate createdAt;

    @OneToMany(mappedBy = "owner", cascade = ALL)
    private List<Book> books = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "vendor")
    private List<Promocode> promoCodes = new ArrayList<>();

    @ManyToMany(cascade = {DETACH, REFRESH, MERGE, PERSIST}, mappedBy = "bookBasket")
    private List<Book> userBasket = new ArrayList<>();

    @ManyToMany(mappedBy = "likes", cascade = MERGE)
    private List<Book> favorite = new ArrayList<>();

    @OneToMany(cascade = ALL, mappedBy = "user")
    private List<PurchasedUserBooks> purchasedUserBooks = new ArrayList<>();

    public void setBook(Book book) {
        this.books.add(book);
    }
    public void setFavoriteBook(Book book) {
        this.favorite.add(book);
    }

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

