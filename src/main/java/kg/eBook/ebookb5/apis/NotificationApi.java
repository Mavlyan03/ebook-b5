package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.NotificationFindByIdResponse;
import kg.eBook.ebookb5.dto.responses.NotificationResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notifications")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationApi {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasAuthority('VENDOR')")
    @Operation(summary = "find all notifications with by authentication")
    public List<NotificationResponse> findAllNotificationsByVendor(Authentication authentication) {
        return notificationService.findAllNotificationsByVendor(authentication);
    }

    @GetMapping("/{notificationId}")
    @PreAuthorize("hasAuthority('VENDOR')")
    @Operation(summary = "find by notification with by id")
    public NotificationFindByIdResponse findByNotificationId(@PathVariable Long notificationId) {
        return notificationService.findByNotificationId(notificationId);
    }

    @PutMapping("/markAsRead")
    @PreAuthorize("hasAuthority('VENDOR')")
    @Operation(summary = "mark as read", description = "mark as read return notifications list")
    public List<NotificationResponse> markAsRead(Authentication authentication) {
        return notificationService.markAsRead(authentication);
    }
}
