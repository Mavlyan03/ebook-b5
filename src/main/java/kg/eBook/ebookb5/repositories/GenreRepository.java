package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("select count (b) from Book b where b.genre = :genre")
    Optional<Long> quantityOfBook(@Param("genre") String genre);
}
