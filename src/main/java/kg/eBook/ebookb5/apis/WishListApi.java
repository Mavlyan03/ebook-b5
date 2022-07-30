package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.models.User;
import kg.eBook.ebookb5.services.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
@CrossOrigin
public class WishListApi {

    private final WishListService wishListService;

    @PutMapping("/{bookId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> addBookToWishList(@PathVariable Long bookId, Authentication authentication) {

        wishListService.addBookToWishList(bookId, authentication);

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
