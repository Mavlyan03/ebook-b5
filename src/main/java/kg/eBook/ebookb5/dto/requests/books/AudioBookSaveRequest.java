package kg.eBook.ebookb5.dto.requests.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudioBookSaveRequest extends BookSaveRequestGeneral{

    private String duration;

    private String audioBook;

}
