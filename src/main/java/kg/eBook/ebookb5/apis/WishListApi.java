package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.responses.userMainPage.FavoriteBooksResponse;
import kg.eBook.ebookb5.db.services.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/wishlist")
@PreAuthorize("hasAuthority('USER')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Wishlist API", description = "Wishlist endpoints for User")
public class WishListApi {

    private final WishListService wishListService;

    @Operation(summary = "Add book", description = "Add books to user wishlist")
    @PutMapping("{bookId}")
    public ResponseEntity<HttpStatus> addBookToWishList(@PathVariable Long bookId, Authentication authentication) {
        wishListService.addBookToWishList(bookId, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Delete book", description = "Removing books from wishlist")
    @GetMapping("{bookId}")
    public ResponseEntity<HttpStatus> removeBookToWishList(@PathVariable Long bookId, Authentication authentication) {
        wishListService.removeBook(bookId, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Get all favorite books", description = "Get all favourite books of user")
    @GetMapping()
    public List<FavoriteBooksResponse> getAllFavoriteBooks(Authentication authentication) {
        return wishListService.getBooks(authentication);
    }

}
