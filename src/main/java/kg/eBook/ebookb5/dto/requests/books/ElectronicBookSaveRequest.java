package kg.eBook.ebookb5.dto.requests.books;

import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.models.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicBookSaveRequest extends BookSaveRequestGeneral{

    private int pageSize;

    private String publishingHouse;

    private String electronicBook;

}
