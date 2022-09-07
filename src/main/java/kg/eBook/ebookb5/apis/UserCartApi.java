package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.BookBasketResponse;
import kg.eBook.ebookb5.services.PromocodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@PreAuthorize("hasAuthority('USER')")
public class UserCartApi {

    private final PromocodeService promocodeService;

    @GetMapping("/check")
    @Operation(summary = "get all books in users cart, check promo code with name", description = "if the promo code is valid, it will return discounted books")
    public List<BookBasketResponse> findAllBooksWithPromocode(@RequestParam String promocodeName, Authentication authentication) {
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

}
