package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.requests.PromocodeRequest;
import kg.eBook.ebookb5.dto.responses.BookBasketResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.db.services.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/promocode")
@Tag(name = "Promocode API", description = "Promo code endpoints for Vendor & User")
public class PromoCodeApi {

    private final PromoCodeService promocodeService;

    @Operation(summary = "Create promo code", description = "Vendor can create promo code")
    @PreAuthorize("hasAuthority('VENDOR')")
    @PostMapping
    public SimpleResponse create(@RequestBody PromocodeRequest promoCodeRequest, Authentication authentication) {
        return promocodeService.createPromoCode(promoCodeRequest, authentication);
    }

    @Operation(summary = "Get all books by promo code", description = "Get all books by promo code for user")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/check")
    public List<BookBasketResponse> findAllBooksWithPromocode(@RequestParam String promocodeName, Authentication authentication) {
        return promocodeService.findAllBooksWithPromocode(promocodeName, authentication);
    }

}