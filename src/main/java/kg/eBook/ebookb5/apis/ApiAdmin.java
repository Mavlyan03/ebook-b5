package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.dto.responses.UserResponse;
import kg.eBook.ebookb5.services.PurchasedUserBooksService;
import kg.eBook.ebookb5.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApiAdmin {

    private final UserService userService;
    private final PurchasedUserBooksService userBooksService;

    @GetMapping("/users")
    @Operation(summary = "find all users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "find by user with id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse findByUserId(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "delete by user with id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteByUserId(@PathVariable Long userId) {
        return userService.deleteByUserId(userId);
    }

    @GetMapping("/bought/{userId}")
    @Operation(summary = "find all purchased user books with user id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<PurchasedUserBooksResponse> purchasedUserBooks(@PathVariable Long userId) {
        return userBooksService.purchasedUserBooks(userId);
    }
}
