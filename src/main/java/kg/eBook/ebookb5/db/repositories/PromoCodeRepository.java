package kg.eBook.ebookb5.db.repositories;

import kg.eBook.ebookb5.db.models.Promocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<Promocode, Long> {

    Boolean existsByName(String name);

    Optional<Promocode> findByName(String name);

}
