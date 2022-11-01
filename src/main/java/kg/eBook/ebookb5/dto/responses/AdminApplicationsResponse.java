package kg.eBook.ebookb5.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminApplicationsResponse {

    private Long id;
    private String mainImage;
    private String name;
    private LocalDate publishedDate;
    private int price;
    private boolean isEnabled;

    public AdminApplicationsResponse(Long id,
                                     String mainImage,
                                     String name,
                                     LocalDate publishedDate,
                                     int price,
                                     boolean isEnabled) {
        this.id = id;
        this.mainImage = mainImage;
        this.name = name;
        this.publishedDate = publishedDate;
        this.price = price;
        this.isEnabled = isEnabled;
    }

}
