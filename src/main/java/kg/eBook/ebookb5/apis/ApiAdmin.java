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

    @GetMapping("/users/{userId}")
    @Operation(summary = "find by user with id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse findByUserId(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "delete by user with id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteByUserId(@PathVariable Long userId) {
        return userService.deleteByUserId(userId);
    }

    @GetMapping("/users/{userId}/purchased")
    @Operation(summary = "find all purchased user books with user id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<PurchasedUserBooksResponse> purchasedUserBooks(@PathVariable Long userId) {
        return userBooksService.purchasedUserBooks(userId);
    }

    @GetMapping("/users/{userId}/favorites")
    @Operation(summary = "find all user favorite books with user id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<PurchasedUserBooksResponse> findAllUsersFavoriteBooks(@PathVariable Long userId) {
        return userService.findAllUsersFavoriteBooks(userId);
    }

    @GetMapping("/users/{userId}/baskets")
    @Operation(summary = "find all user basket books with user id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<PurchasedUserBooksResponse> findAllUserBooksInBasket(@PathVariable Long userId) {
        return userService.findAllUserBooksInBasket(userId);
    }
}
