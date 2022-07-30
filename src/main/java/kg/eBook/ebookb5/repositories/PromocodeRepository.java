package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.models.Promocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocodeRepository extends JpaRepository<Promocode, Long> {
}
