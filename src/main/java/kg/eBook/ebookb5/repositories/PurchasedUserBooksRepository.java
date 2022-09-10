package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse;
import kg.eBook.ebookb5.models.PurchasedUserBooks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchasedUserBooksRepository extends JpaRepository<PurchasedUserBooks, Long> {

    @Query("select new kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse(" +
            "p.id, p.bookName, p.price, p.bookAuthor, p.purchaseDate, p.quantityOfBook, p.bookMainImage, " +
            "p.promocode, p.discount) from PurchasedUserBooks p where p.user.id = :id")
    Page<PurchasedUserBooksResponse> findAllByUserId(@Param("id") Long userId, Pageable pageable);

    Boolean existsPurchasedUserBooksByBookId(Long bookId);
}
