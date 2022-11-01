package kg.eBook.ebookb5.db.repositories;

import kg.eBook.ebookb5.db.models.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

    Boolean existsByName(String name);

    Optional<PromoCode> findByName(String name);

}
