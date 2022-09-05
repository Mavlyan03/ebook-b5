package kg.eBook.ebookb5.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import kg.eBook.ebookb5.models.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ABookVendorResponse {

    private Long id;
    private String name;
    private String mainImage;
    private int price;

    @JsonFormat(pattern="dd-MM-yyyy")
    @ApiModelProperty(dataType = "java.sql.Date")
    private LocalDate dateOfRegistration;
    private Integer favorite;
    private Integer basket;
}
