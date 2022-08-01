package kg.eBook.ebookb5.dto.requests.books;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSave<bookType extends BookSaveRequestGeneral> {
    private bookType bookType;
}
