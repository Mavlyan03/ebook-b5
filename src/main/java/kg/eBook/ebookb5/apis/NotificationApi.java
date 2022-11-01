package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.responses.NotificationFindByIdResponse;
import kg.eBook.ebookb5.dto.responses.NotificationResponse;
import kg.eBook.ebookb5.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notifications")
@PreAuthorize("hasAuthority('VENDOR')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Notification API", description = "Notification endpoints for Vendor")
public class NotificationApi {

    private final NotificationService notificationService;

    @Operation(summary = "Get all notifications")
    @GetMapping
    public List<NotificationResponse> findAllNotificationsByVendor(Authentication authentication) {
        return notificationService.findAllNotificationsByVendor(authentication);
    }

    @Operation(summary = "Get notification by id")
    @GetMapping("{notificationId}")
    public NotificationFindByIdResponse findByNotificationId(@PathVariable Long notificationId) {
        return notificationService.findByNotificationId(notificationId);
    }

    @Operation(summary = "Mark as read", description = "Mark as read return notifications list")
    @PutMapping("mark-as-read")
    public List<NotificationResponse> markAsRead(Authentication authentication) {
        return notificationService.markAsRead(authentication);
    }

}
