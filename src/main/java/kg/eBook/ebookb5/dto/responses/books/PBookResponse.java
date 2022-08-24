package kg.eBook.ebookb5.dto.responses.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PBookResponse extends BookResponseGeneral{

    private int pageSize;

    private String publishingHouse;

    private int quantityOfBook;

}
