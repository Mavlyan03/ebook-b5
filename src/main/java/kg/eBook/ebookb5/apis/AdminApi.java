package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.responses.AdminBooksResponse;
import kg.eBook.ebookb5.dto.responses.OperationsHistoryResponse;
import kg.eBook.ebookb5.dto.responses.PurchasedUserBooksResponse;
import kg.eBook.ebookb5.dto.responses.UserResponse;
import kg.eBook.ebookb5.dto.responses.VendorResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.db.services.BookService;
import kg.eBook.ebookb5.db.services.PurchasedUserBooksService;
import kg.eBook.ebookb5.db.services.UserService;
import kg.eBook.ebookb5.db.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Admin API", description = "Admin user/vendor/book endpoints")
public class AdminApi {

    private final UserService userService;
    private final PurchasedUserBooksService userBooksService;
    private final VendorService vendorService;
    private final BookService bookService;


    @Operation(summary = "Get all users", description = "User with role 'ADMIN' can get all users")
    @GetMapping("users")
    public List<UserResponse> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("users/{userId}/operationsHistory")
    @Operation(summary = "Get user operation history", description = "Find all purchased user books with user id")
    public OperationsHistoryResponse operationsHistory(@PathVariable Long userId,
                                                       @RequestParam(required = false) int page,
                                                       @RequestParam(required = false) int size) {
        List<PurchasedUserBooksResponse> allUsersFavoriteBooks = userService.findAllUsersFavoriteBooks(userId);
        List<PurchasedUserBooksResponse> allUserBooksInBasket = userService.findAllUserBooksInBasket(userId);
        Page<PurchasedUserBooksResponse> purchasedUserBooksResponses = userBooksService.purchasedUserBooks(userId, page, size);

        return new OperationsHistoryResponse(purchasedUserBooksResponses,
                allUsersFavoriteBooks, allUserBooksInBasket);
    }

    @GetMapping("vendors")
    @Operation(summary = "Get all vendors", description = "User with role 'ADMIN' can get all vendors")
    public List<VendorResponse> findAllVendors() {
        return vendorService.findAllVendors();
    }

    @GetMapping("books")
    @Operation(summary = "Get all books", description = "User with role 'ADMIN' can get all books")
    public Page<AdminBooksResponse> getAllBooks(@RequestParam(required = false) Long genreId,
                                                @RequestParam(required = false) BookType bookType,
                                                @RequestParam(required = false, defaultValue = "1") int page,
                                                @RequestParam(required = false, defaultValue = "8") int size) {
        return bookService.findAllBooks(genreId,
                bookType,
                page, size
        );
    }

}
