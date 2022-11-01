package kg.eBook.ebookb5.dto.requests.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudioBookSaveRequest extends BookSaveRequestGeneral {

    @NotEmpty(message = "Поле не может быть пустым")
    private String duration;

    @NotEmpty(message = "Поле не может быть пустым")
    private String audioBookFragment;

    @NotEmpty(message = "Поле не может быть пустым")
    private String audioBook;

}
