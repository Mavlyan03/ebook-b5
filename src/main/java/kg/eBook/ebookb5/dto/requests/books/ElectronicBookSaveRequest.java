package kg.eBook.ebookb5.dto.requests.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicBookSaveRequest extends BookSaveRequestGeneral{

    @NotNull(message = "Поле не может быть пустым")
    private int pageSize;

    @NotEmpty(message = "Поле не может быть пустым")
    private String publishingHouse;

    @NotEmpty(message = "Поле не может быть пустым")
    private String electronicBook;

}
