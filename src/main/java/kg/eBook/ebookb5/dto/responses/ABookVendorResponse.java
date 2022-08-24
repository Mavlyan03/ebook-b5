package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.models.Book;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ABookVendorResponse {

    private Long id;
    private String name;
    private String mainImage;
    private int price;
    private LocalDate dateOfRegistration;
    private Integer favorite;
    private Integer basket;

    public ABookVendorResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.mainImage = book.getMainImage();
        this.price = book.getPrice();
        this.basket = book.getBookBasket().size();
        this.favorite = book.getLikes().size();
        this.dateOfRegistration = book.getPublishedDate();
    }

    public static List<ABookVendorResponse> viewBooks(List<Book> books) {
        List<ABookVendorResponse> aBookVendorResponses = new ArrayList<>();
        for (Book book : books) {
            aBookVendorResponses.add(new ABookVendorResponse(book));
        }
        return aBookVendorResponses;
    }
}
