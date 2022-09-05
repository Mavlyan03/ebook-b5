package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.requests.UserRequest;
import kg.eBook.ebookb5.dto.responses.OperationsHistoryResponse;
import kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.dto.responses.UserResponse;
import kg.eBook.ebookb5.services.PurchasedUserBooksService;
import kg.eBook.ebookb5.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('USER')")
public class UserApi {

    private final UserService userService;
    private final PurchasedUserBooksService userBooksService;

    @PutMapping
    @Operation(summary = "update by user with authentication")
    public SimpleResponse updateByUser(Authentication authentication,
                                       @RequestBody UserRequest userRequest) {
        return userService.updateByUser(authentication, userRequest);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "find by user with id")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public UserResponse findByUserId(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "delete by user with user id")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public SimpleResponse deleteByUser(@PathVariable Long userId)  {
        return userService.deleteByUserId(userId);
    }

    @GetMapping("/{userId}/operationsHistory")
    @Operation(summary = "find all purchased user books with user id")
    public List<PurchasedUserBooksResponse> operationsHistory(@PathVariable Long userId) {
        return userBooksService.purchasedUserBooks(userId);
    }
}
