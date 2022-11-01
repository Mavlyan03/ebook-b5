package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.db.models.Genre;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreResponse {

    Long id;
    String name;
    Long quantityOfBook;

    public GenreResponse(Genre genre, Long quantityOfBook) {
        this.id = genre.getId();
        this.name = genre.getName();
        this.quantityOfBook = quantityOfBook;
    }

}
