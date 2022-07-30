package kg.eBook.ebookb5.dto.requests.books;

import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.models.Genre;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class AudioBookSaveRequest extends BookSaveRequestGeneral{

    public AudioBookSaveRequest(String mainImage, String secondImage, String thirdImage, String name, Genre genre, int price, String author, String description, Language language, int yearOfIssue, int discount, boolean bestseller, String fragment) {
        super(mainImage, secondImage, thirdImage, name, genre, price, author, description, language, yearOfIssue, discount, bestseller, fragment);
    }

    private LocalTime duration;

    private String audioBook;

}
