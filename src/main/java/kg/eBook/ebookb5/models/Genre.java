package kg.eBook.ebookb5.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_gen")
    @SequenceGenerator(name = "genre_gen", sequenceName = "genre_seq", allocationSize = 1)

    private Long id;

    private String name;

    @OneToMany(mappedBy = "genre")
    private List<Book> books;
}
