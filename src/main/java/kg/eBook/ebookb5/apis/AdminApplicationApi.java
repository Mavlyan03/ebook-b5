package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/application")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminApplicationApi {

    private final NotificationService notificationService;

    @PostMapping("/books/{bookId}/accepted")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "accepted a book", description = "accepted a book with by id")
    public SimpleResponse acceptedBook(@PathVariable Long bookId) {
        return notificationService.acceptedBook(bookId);
    }

    @PostMapping("/books/{bookId}/rejected")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "rejected a book", description = "rejected a book with by id")
    public SimpleResponse rejectedBook(@PathVariable Long bookId, @RequestParam String description) {
        return notificationService.rejectedBook(bookId, description);
    }

}
