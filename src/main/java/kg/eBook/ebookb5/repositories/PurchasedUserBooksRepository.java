package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.models.PurchasedUserBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedUserBooksRepository extends JpaRepository<PurchasedUserBooks, Long> {

    @Query("select p from PurchasedUserBooks p where p.user.id = :id")
    List<PurchasedUserBooks> findAllByUserId(@Param("id") Long userId);

//    @Query("select p from PurchasedUserBooks p where exists (select p from PurchasedUserBooks p where p.bookId = : id)")
//    Boolean existsPurchasedBooks(@Param("id") Long id);

    Boolean existsPurchasedUserBooksByBookId(Long bookId);
}
