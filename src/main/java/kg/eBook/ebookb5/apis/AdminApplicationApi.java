package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.responses.ApplicationResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.services.BookService;
import kg.eBook.ebookb5.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("api/admin/applications")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Admin API", description = "Admin book application endpoints")
public class AdminApplicationApi {

    private final NotificationService notificationService;
    private final BookService bookService;

    @Operation(summary = "Accepted a book", description = "Accepted a book with by id")
    @PostMapping("books/{bookId}/accepted")
    public SimpleResponse acceptedBook(@PathVariable Long bookId) {
        return notificationService.acceptedBook(bookId);
    }

    @Operation(summary = "Rejected a book", description = "Rejected a book with by id")
    @PostMapping("books/{bookId}/rejected")
    public SimpleResponse rejectedBook(@PathVariable Long bookId, @RequestParam String description) {
        return notificationService.rejectedBook(bookId, description);
    }

    @Operation(summary = "Get applications", description = "User with role 'Admin' can get book applications")
    @GetMapping
    public ApplicationResponse getApplications(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "8") int size) {
        return bookService.applications(
                page,
                size
        );
    }
}
