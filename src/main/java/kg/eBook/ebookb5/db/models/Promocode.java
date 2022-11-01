package kg.eBook.ebookb5.db.models;

import kg.eBook.ebookb5.dto.requests.PromocodeRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "promocodes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Promocode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promo_gen")
    @SequenceGenerator(name = "promo_gen", sequenceName = "promo_seq", initialValue = 2, allocationSize = 1)
    private Long id;

    private String name;
    private int discount;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;

    @ManyToOne
    private User vendor;

    public Promocode(PromocodeRequest request) {
        this.name = request.getName();
        this.discount = request.getDiscount();
        this.dateOfStart = request.getDateOfStart();
        this.dateOfFinish = request.getDateOfFinish();
    }
}