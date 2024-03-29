package kg.eBook.ebookb5.db.models;

import kg.eBook.ebookb5.dto.requests.PromoCodeRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "promocodes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromoCode {

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

    public PromoCode(PromoCodeRequest request) {
        this.name = request.getName();
        this.discount = request.getDiscount();
        this.dateOfStart = request.getDateOfStart();
        this.dateOfFinish = request.getDateOfFinish();
    }

}