package kg.eBook.ebookb5.db.repositories;

import kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse;
import kg.eBook.ebookb5.db.models.PurchasedUserBooks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedUserBooksRepository extends JpaRepository<PurchasedUserBooks, Long> {

    @Query("select new kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse(" +
            "p.id, p.bookName, p.price, p.bookAuthor, p.purchaseDate, p.quantityOfBook, p.bookMainImage, " +
            "p.promocode, p.discount) from PurchasedUserBooks p where p.user.id = :userId")
    Page<PurchasedUserBooksResponse> findAllByUserId(Long userId, Pageable pageable);

    Boolean existsPurchasedUserBooksByBookId(Long bookId);
}
