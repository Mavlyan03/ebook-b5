package kg.eBook.ebookb5.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import kg.eBook.ebookb5.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class aBookVendorResponse {

    private Long id;
    private String name;
    private String mainImage;
    private int price;
    private BookStatus bookStatus;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(dataType = "java.sql.Date")
    private LocalDate dateOfRegistration;

    private Integer favorite;
    private Integer basket;

}
