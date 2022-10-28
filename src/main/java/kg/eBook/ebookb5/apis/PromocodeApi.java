package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.requests.PromocodeRequest;
import kg.eBook.ebookb5.dto.responses.BookBasketResponse;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.services.PromocodeService;
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
@Tag(name = "Promocode API", description = "Vendor can create promo code, and 'user check that promo code")
public class PromocodeApi {

    private final PromocodeService promocodeService;

    @PreAuthorize("hasAuthority('VENDOR')")
    @Operation(summary = "Create promo code", description = "Vendor can create promo code")
    @PostMapping
    public SimpleResponse create(@RequestBody PromocodeRequest promoCodeRequest,
                                 Authentication authentication) {
        return promocodeService.createPromoCode(promoCodeRequest, authentication);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "User can check promo code with name", description = "User if the promo code is valid, it will return discounted books")
    @GetMapping("/check")
    public List<BookBasketResponse> findAllBooksWithPromocode(@RequestParam String promocodeName,
                                                              Authentication authentication) {
        return promocodeService.findAllBooksWithPromocode(promocodeName, authentication);
    }
}