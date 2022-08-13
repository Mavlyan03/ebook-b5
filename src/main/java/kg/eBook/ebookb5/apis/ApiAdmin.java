package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.OperationsHistoryResponse;
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

    @GetMapping("/users/{userId}/operationsHistory")
    @Operation(summary = "find all purchased user books with user id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public OperationsHistoryResponse operationsHistory(@PathVariable Long userId) {
        List<PurchasedUserBooksResponse> allUsersFavoriteBooks = userService.findAllUsersFavoriteBooks(userId);
        List<PurchasedUserBooksResponse> allUserBooksInBasket = userService.findAllUserBooksInBasket(userId);
        List<PurchasedUserBooksResponse> purchasedUserBooksResponses = userBooksService.purchasedUserBooks(userId);

        return  new OperationsHistoryResponse(purchasedUserBooksResponses,
                allUsersFavoriteBooks, allUserBooksInBasket);
    }
}
