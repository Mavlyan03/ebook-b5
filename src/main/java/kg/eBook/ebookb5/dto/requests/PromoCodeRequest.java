package kg.eBook.ebookb5.dto.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.time.LocalDate;

@Getter
@Setter
public class PromoCodeRequest {

    private String name;
    private int discount;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfStart;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfFinish;

}
