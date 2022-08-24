package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.AdminApplicationsResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.services.BookService;
import kg.eBook.ebookb5.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("api/admin/application")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminApplicationApi {

    private final NotificationService notificationService;

    private final BookService bookService;

    @PostMapping("/books/{bookId}/accepted")
    @Operation(summary = "accepted a book", description = "accepted a book with by id")
    public SimpleResponse acceptedBook(@PathVariable Long bookId) {
        return notificationService.acceptedBook(bookId);
    }

    @PostMapping("/books/{bookId}/rejected")
    @Operation(summary = "rejected a book", description = "rejected a book with by id")
    public SimpleResponse rejectedBook(@PathVariable Long bookId, @RequestParam String description) {
        return notificationService.rejectedBook(bookId, description);
    }

    @GetMapping("/applications")
    @Operation(summary = "Get applications", description = "User with role 'Admin' can get book applications")
    public List<AdminApplicationsResponse> getApplications(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "8") int size
    ) {
        return bookService.getApplications(
                page,
                size
        );
    }

}
