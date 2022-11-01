package kg.eBook.ebookb5.db.repositories;

import kg.eBook.ebookb5.db.models.MailingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailingListRepository extends JpaRepository<MailingList, Long> {
}