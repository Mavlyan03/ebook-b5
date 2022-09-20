package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.books.BookResponseGeneral;
import kg.eBook.ebookb5.dto.responses.userMainPage.FavoriteBooksResponse;
import kg.eBook.ebookb5.services.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
@CrossOrigin
@PreAuthorize("hasAuthority('USER')")
public class WishListApi {

    private final WishListService wishListService;

    @PutMapping("/{bookId}")
    @Operation(summary = "add book to wish list")
    public ResponseEntity<HttpStatus> addBookToWishList(@PathVariable Long bookId, Authentication authentication) {
        wishListService.addBookToWishList(bookId, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "remove book to wish list")
    public ResponseEntity<HttpStatus> removeBookToWishList(@PathVariable Long bookId, Authentication authentication) {
        wishListService.removeBook(bookId, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "find all favorite books")
    public List<FavoriteBooksResponse> getAllFavoriteBooks(Authentication authentication) {
        return wishListService.getBooks(authentication);
    }

}
