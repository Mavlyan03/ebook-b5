package kg.eBook.ebookb5.dto.requests.books;

import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.models.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudioBookSaveRequest extends BookSaveRequestGeneral{

    private LocalTime duration;

    private String audioBook;

}
