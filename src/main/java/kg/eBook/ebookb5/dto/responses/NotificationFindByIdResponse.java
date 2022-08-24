package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.models.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificationFindByIdResponse {

    private BookStatus bookStatus;

    private LocalDate dateOfStatus;

    private String description;

    public NotificationFindByIdResponse(Notification notification) {
        this.bookStatus = notification.getBookStatus();
        this.dateOfStatus = notification.getCreatedAt();
        this.description = notification.getDescription();
    }
}
