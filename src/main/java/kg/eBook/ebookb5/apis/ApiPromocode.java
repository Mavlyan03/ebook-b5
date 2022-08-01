package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.requests.PromocodeRequest;
import kg.eBook.ebookb5.dto.responses.BookBasketResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.services.PromocodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(maxAge = 5000)
@RequestMapping("/api/promocode")
public class ApiPromocode {

    private final PromocodeService promocodeService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('VENDOR')")
    @Operation(summary = "create promo code", description = "we can create promo code")
    public SimpleResponse create(@RequestBody PromocodeRequest promoCodeRequest, Authentication authentication) {

        return promocodeService.createPromoCode(promoCodeRequest, authentication);
    }

    @GetMapping("/check")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "find promo code with name", description = "if the promo code is valid, it will return discounted books")
    public List<BookBasketResponse> findAllBooksWithPromocode(@RequestParam String promocodeName, Authentication authentication) {

        return promocodeService.findAllBooksWithPromocode(promocodeName, authentication);
    }
}