package kg.eBook.ebookb5.dto.requests.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaperBookSaveRequest extends BookSaveRequestGeneral{

    @NotNull(message = "Поле не может быть пустым")
    private int pageSize;

    @NotEmpty(message = "Поле не может быть пустым")
    private String publishingHouse;

    @NotNull(message = "Поле не может быть пустым")
    private int quantityOfBook;
}
