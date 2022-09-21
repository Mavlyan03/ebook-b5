package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.BookBasketResponse;
import kg.eBook.ebookb5.services.PromocodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@PreAuthorize("hasAuthority('USER')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCartApi {

    private final PromocodeService promocodeService;

    @GetMapping("/check")
    @Operation(summary = "get all books in users cart, check promo code with name", description = "if the promo code is valid, it will return discounted books")
    public List<BookBasketResponse> findAllBooksWithPromocode(
            @RequestParam(required = false) String promocodeName, Authentication authentication) {
        return promocodeService.findAllBooksWithPromocode(promocodeName, authentication);
    }

    @PostMapping("/plus-button/{bookId}")
    public int plusButton(@PathVariable Long bookId, @RequestParam Integer plus) {
        return promocodeService.increaseBooksToBuy(bookId, plus);
    }

    @PostMapping("/minus-button/{bookId}")
    public int minusButton(@PathVariable Long bookId, @RequestParam Integer minus, Authentication authentication) {
        return promocodeService.decreaseBookToBuy(bookId, minus, authentication);
    }

    @PostMapping("/{bookId}")
    @Operation(summary = "Adding book to cart")
    public ResponseEntity<HttpStatus> addBookToBasketList(@PathVariable Long bookId, Authentication authentication) {
        promocodeService.addBookToBasketList(bookId, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{bookId}")
    @Operation(summary = "Remove book to cart")
    public ResponseEntity<HttpStatus> removeBookToBasketList(@PathVariable Long bookId, Authentication authentication) {
        promocodeService.removeBookToBasketList(bookId, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Remove all book to cart")
    public ResponseEntity<HttpStatus> removeAllBooksToBasketList(Authentication authentication) {
        promocodeService.removeAllBooksToBasketList(authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
