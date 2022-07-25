package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.models.MailList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailListRepository extends JpaRepository<MailList, Long> {
}
