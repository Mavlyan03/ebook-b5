package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.requests.VendorProfileRequest;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.dto.responses.VendorResponse;
import kg.eBook.ebookb5.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vendors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VendorApi {

    private final VendorService vendorService;

    @PutMapping("/vendor")
    @Operation(summary = "update by vendor")
    public VendorResponse update(Authentication authentication,
                                 @RequestBody VendorProfileRequest vendorProfileRequest) {
        return vendorService.update(authentication, vendorProfileRequest);
    }

    @DeleteMapping("/{vendorId}")
    @Operation(summary = "delete by vendor with vendor id")
    public SimpleResponse delete(@PathVariable Long vendorId) {
        return vendorService.deleteByVendorId(vendorId);
    }
}
