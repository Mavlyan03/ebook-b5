package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.models.PurchasedUserBooks;
import kg.eBook.ebookb5.models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
public class PurchasedUserBooksResponse {

    private Long id;

    private String name;

    private int price;

    private String author;

    private LocalDate purchaseDate;

    private int quantityOfBook;

    private String mainImage;

    private int promocode;

    public PurchasedUserBooksResponse(PurchasedUserBooks userBooks) {
       this.id = userBooks.getId();
       this.name = userBooks.getName();
       this.price = userBooks.getPrice();
       this.author = userBooks.getAuthor();
       this.purchaseDate = userBooks.getPurchaseDate();
       this.quantityOfBook = userBooks.getQuantityOfBook();
       this.mainImage = userBooks.getMainImage();
       this.promocode = userBooks.getPromocode();
    }
}
