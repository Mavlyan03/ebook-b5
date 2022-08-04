package kg.eBook.ebookb5.dto.requests.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaperBookSaveRequest extends BookSaveRequestGeneral{

    @NotEmpty(message = "Поле не может быть пустым")
    @Max(value = 3000)
    private int pageSize;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max = 1000)
    private String publishingHouse;

    @NotEmpty(message = "Поле не может быть пустым")
    @Max(value = 5000)
    private int quantityOfBook;
}
