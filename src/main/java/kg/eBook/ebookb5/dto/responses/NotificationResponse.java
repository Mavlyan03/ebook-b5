package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.db.models.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NotificationResponse {

    private Long id;
    private BookStatus bookStatus;
    private LocalDate dateOfStatus;
    private boolean read;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.bookStatus = notification.getBookStatus();
        this.dateOfStatus = notification.getCreatedAt();
        this.read = notification.isRead();
    }

    public static List<NotificationResponse> view(List<Notification> notifications) {
        List<NotificationResponse> notificationResponses = new ArrayList<>();

        for (Notification notification : notifications) {
            notificationResponses.add(new NotificationResponse(notification));
        }
        return notificationResponses;
    }

    public NotificationResponse(Long id, BookStatus bookStatus, LocalDate dateOfStatus, boolean read) {
        this.id = id;
        this.bookStatus = bookStatus;
        this.dateOfStatus = dateOfStatus;
        this.read = read;
    }

}
