package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.dto.responses.NotificationResponse;
import kg.eBook.ebookb5.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByBookId(Long bookId);

    @Query("select new kg.eBook.ebookb5.dto.responses.NotificationResponse " +
            "(n.id, n.bookStatus, n.createdAt, n.read ) from Notification n " +
            "where n.vendor.id=:vendorId " +
            "order by n.read asc ")
    List<NotificationResponse> findAllNotifications(Long vendorId);
}