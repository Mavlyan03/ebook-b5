package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByBookId(Long bookId);
}