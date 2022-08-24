package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.*;
import kg.eBook.ebookb5.enums.AboutBooks;
import kg.eBook.ebookb5.services.PurchasedUserBooksService;
import kg.eBook.ebookb5.services.UserService;
import kg.eBook.ebookb5.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('ADMIN')")
public class ApiAdmin {

    private final UserService userService;
    private final PurchasedUserBooksService userBooksService;

    private final VendorService vendorService;

    @GetMapping("/users")
    @Operation(summary = "find all users")
    public List<UserResponse> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "find by user with id")
    public UserResponse findByUserId(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/users/{userId}/operationsHistory")
    @Operation(summary = "find all purchased user books with user id")
    public OperationsHistoryResponse operationsHistory(@PathVariable Long userId) {
        List<PurchasedUserBooksResponse> allUsersFavoriteBooks = userService.findAllUsersFavoriteBooks(userId);
        List<PurchasedUserBooksResponse> allUserBooksInBasket = userService.findAllUserBooksInBasket(userId);
        List<PurchasedUserBooksResponse> purchasedUserBooksResponses = userBooksService.purchasedUserBooks(userId);

        return  new OperationsHistoryResponse(purchasedUserBooksResponses,
                allUsersFavoriteBooks, allUserBooksInBasket);
    }

    @GetMapping("/vendors")
    @Operation(summary = "find all vendors")
    public List<VendorResponse> findAllVendors() {
        return vendorService.findAllVendors();
    }

    @GetMapping("/vendors/{vendorId}")
    @Operation(summary = "find by vendor with id")
    public VendorResponse findByVendor(@PathVariable Long vendorId) {
        return vendorService.findByVendor(vendorId);
    }
}
