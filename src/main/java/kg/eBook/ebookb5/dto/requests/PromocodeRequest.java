package kg.eBook.ebookb5.dto.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class PromocodeRequest {
    private String name;
    private int discount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfFinish;
}
