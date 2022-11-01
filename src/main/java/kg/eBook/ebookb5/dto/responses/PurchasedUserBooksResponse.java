package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.db.models.Book;
import kg.eBook.ebookb5.db.models.PurchasedUserBooks;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PurchasedUserBooksResponse {

    private Long bookId;
    private String name;
    private int price;
    private String author;
    private LocalDate purchaseDate;
    private int quantityOfBook;
    private String mainImage;
    private int promocode;
    private int discount;
    public PurchasedUserBooksResponse(Long bookId, String name, int price, String author,
                                      LocalDate purchaseDate, int quantityOfBook, String mainImage,
                                      int promocode, int discount) {
        this.bookId = bookId;
        this.name = name;
        this.price = price;
        this.author = author;
        this.purchaseDate = purchaseDate;
        this.quantityOfBook = quantityOfBook;
        this.mainImage = mainImage;
        this.promocode = promocode;
        this.discount = discount;
    }

    private LocalDate dateTheBookWasAddedToFavorites;

    public PurchasedUserBooksResponse(PurchasedUserBooks userBooks) {
       this.bookId = userBooks.getBookId();
       this.name = userBooks.getBookName();
       this.price = userBooks.getPrice();
       this.author = userBooks.getBookAuthor();
       this.purchaseDate = userBooks.getPurchaseDate();
       this.quantityOfBook = userBooks.getQuantityOfBook();
       this.mainImage = userBooks.getBookMainImage();
       this.promocode = userBooks.getPromocode();
    }

    public PurchasedUserBooksResponse(Book userBooks) {
        this.bookId = userBooks.getId();
        this.name = userBooks.getName();
        this.price = userBooks.getPrice();
        this.author = userBooks.getAuthor();
        this.dateTheBookWasAddedToFavorites = userBooks.getDateTheBookWasAddedToFavorites();
        this.quantityOfBook = userBooks.getQuantityOfBook();
        this.mainImage = userBooks.getMainImage();
    }

    public static List<PurchasedUserBooksResponse> viewUserBooks(List<Book> userBooks) {
        List<PurchasedUserBooksResponse> booksResponse = new ArrayList<>();
        for (Book book: userBooks) {
            booksResponse.add(new PurchasedUserBooksResponse(book));
        }
        return booksResponse;
    }
}
