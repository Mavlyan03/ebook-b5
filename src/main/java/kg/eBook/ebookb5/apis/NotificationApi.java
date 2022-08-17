package kg.eBook.ebookb5.apis;

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

    @PostMapping("/books/{bookId}/accepted")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse acceptedBook(@PathVariable Long bookId) {
        return notificationService.acceptedBook(bookId);
    }

    @PostMapping("/books/{bookId}/rejected")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse rejectedBook(@PathVariable Long bookId, @RequestParam String description) {
        return notificationService.rejectedBook(bookId, description);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VENDOR')")
    public List<NotificationResponse> findAllNotificationsByVendor(Authentication authentication) {
        return notificationService.findAllNotificationsByVendor(authentication);
    }

    @GetMapping("/{notificationId}")
    @PreAuthorize("hasAuthority('VENDOR')")
    public NotificationFindByIdResponse findByNotificationId(@PathVariable Long notificationId) {
        return notificationService.findByNotificationId(notificationId);
    }

    @PutMapping("/markAsRead")
    @PreAuthorize("hasAuthority('VENDOR')")
    public List<NotificationResponse> markAsRead(Authentication authentication) {
        return notificationService.markAsRead(authentication);
    }
}
