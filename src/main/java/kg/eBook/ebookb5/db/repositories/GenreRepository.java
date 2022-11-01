package kg.eBook.ebookb5.db.repositories;

import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.db.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("select count(b) from Book b where b.genre.id = :id")
    Optional<Long> quantityOfBook(Long id);

    @Query("select count(b) from Book b where b.genre.id = :id and b.bookStatus = :bookStatus")
    Optional<Long> quantityOfBookACCEPTED(BookStatus bookStatus, Long id);
}
