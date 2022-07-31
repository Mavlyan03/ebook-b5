package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.requests.PromocodeRequest;
import kg.eBook.ebookb5.dto.responses.BookBasketResponse;
import kg.eBook.ebookb5.dto.responses.SimplyResponse;
import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.services.PromocodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/promocode")
public class ApiPromocode {

    private final PromocodeService promocodeService;

    @PostMapping("/create")
    @Operation(summary = "create promo code", description = "we can create promo code")
    public SimplyResponse create(@RequestBody PromocodeRequest promoCodeRequest, Authentication authentication) {

        return promocodeService.createPromoCode(promoCodeRequest, authentication);
    }

    @GetMapping("/find")
    public List<BookBasketResponse> findAllBooksWithPromocode(@RequestBody String promocodeName, Authentication authentication) {

        return promocodeService.findAllBooksWithPromocode(promocodeName, authentication);
    }
}