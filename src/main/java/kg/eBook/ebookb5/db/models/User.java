package kg.eBook.ebookb5.db.models;

import kg.eBook.ebookb5.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", initialValue = 4, allocationSize = 1)
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

    @OneToMany(mappedBy = "vendor", cascade = ALL)
    private List<PromoCode> promoCodes = new ArrayList<>();

    @ManyToMany(cascade = {DETACH, REFRESH, MERGE, PERSIST}, mappedBy = "bookBasket")
    private List<Book> userBasket = new ArrayList<>();

    @ManyToMany(mappedBy = "likes", cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private List<Book> favorite = new ArrayList<>();

    @OneToMany(cascade = ALL, mappedBy = "user")
    private List<PurchasedUserBooks> purchasedUserBooks = new ArrayList<>();

    @OneToMany(cascade = ALL, mappedBy = "vendor")
    private List<Notification> notifications = new ArrayList<>();

    public void setBook(Book book) {
        this.books.add(book);
    }

    public void setFavoriteBook(Book book) {
        this.favorite.add(book);
    }

    public void setBasketBook(Book book) {
        this.userBasket.add(book);
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

