package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.requests.PromocodeRequest;
import kg.eBook.ebookb5.dto.responses.SimplyResponse;
import kg.eBook.ebookb5.services.PromocodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/promocode")
public class ApiPromocode {

    private final PromocodeService promoCodeService;

    @PostMapping("/create")
    @Operation(summary = "create promo code", description = "we can create promo code")
    public SimplyResponse create(@RequestBody PromocodeRequest promoCodeRequest, Authentication authentication) {

        return promoCodeService.createPromoCode(promoCodeRequest, authentication);
    }
}