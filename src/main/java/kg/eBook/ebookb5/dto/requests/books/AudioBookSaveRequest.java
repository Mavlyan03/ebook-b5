package kg.eBook.ebookb5.dto.requests.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudioBookSaveRequest extends BookSaveRequestGeneral{

    private LocalTime duration;

    private String audioBook;

}
