package kg.eBook.ebookb5.models;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "promo_codes")
@Getter
@Setter
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "promoCode_gen", sequenceName = "promoCode_seq", allocationSize = 1)
    private Long id;

    private String name;
    private int discount;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;

    @ManyToOne
    private User vendor;
}

