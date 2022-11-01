package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.responses.BookBasketResponse;
import kg.eBook.ebookb5.db.services.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cart")
@PreAuthorize("hasAuthority('USER')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Cart API", description = "Cart endpoints for user")
public class UserCartApi {

    private final PromoCodeService promocodeService;

    @Operation(summary = "Get all books in users cart", description = "If the promo code is valid, it will return discounted books")
    @GetMapping("check")
    public List<BookBasketResponse> findAllBooksWithPromocode(
            @RequestParam(required = false) String promocodeName, Authentication authentication) {
        return promocodeService.findAllBooksWithPromoCode(promocodeName, authentication);
    }

    @Operation(summary = "Increase book quantity", description = "Increase book quantity in cart by user")
    @PostMapping("plus-button/{bookId}")
    public int plusButton(@PathVariable Long bookId, @RequestParam Integer plus) {
        return promocodeService.increaseBooksToBuy(bookId, plus);
    }

    @Operation(summary = "Decrease book quantity", description = "Decrease book quantity in cart by user")
    @PostMapping("minus-button/{bookId}")
    public int minusButton(@PathVariable Long bookId, @RequestParam Integer minus, Authentication authentication) {
        return promocodeService.decreaseBookToBuy(bookId, minus, authentication);
    }

    @Operation(summary = "Add book to cart", description = "Adding book to cart by user")
    @PostMapping("/{bookId}")
    public ResponseEntity<HttpStatus> addBookToBasketList(@PathVariable Long bookId, Authentication authentication) {
        promocodeService.addBookToBasketList(bookId, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Delete book from cart", description = "Removing book from cart by user")
    @PutMapping("/{bookId}")
    public ResponseEntity<HttpStatus> removeBookToBasketList(@PathVariable Long bookId, Authentication authentication) {
        promocodeService.removeBookToBasketList(bookId, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Delete all book from cart", description = "Removing all books from cart by user")
    @PutMapping
    public ResponseEntity<HttpStatus> removeAllBooksToBasketList(Authentication authentication) {
        promocodeService.removeAllBooksToBasketList(authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
