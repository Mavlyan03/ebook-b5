package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.requests.VendorProfileRequest;
import kg.eBook.ebookb5.dto.responses.SimpleResponse;
import kg.eBook.ebookb5.dto.responses.VendorResponse;
import kg.eBook.ebookb5.dto.responses.ABookVendorResponse;
import kg.eBook.ebookb5.enums.AboutBooks;
import kg.eBook.ebookb5.exceptions.WrongEmailException;
import kg.eBook.ebookb5.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vendors")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Vendor API", description = "Vendor endpoints for Vendor & Admin")
public class VendorApi {

    private final VendorService vendorService;

    @Operation(summary = "Vendor profile update", description = "Vendor profile update")
    @PreAuthorize("hasAuthority('VENDOR')")
    @PutMapping("profile")
    public VendorResponse update(Authentication authentication,
                                 @RequestBody @Valid VendorProfileRequest vendorProfileRequest,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongEmailException("Неправильный адрес электронной почты");
        }
        return vendorService.update(authentication, vendorProfileRequest);
    }

    @Operation(summary = "Get vendor by id", description = "Get vendor profile with id by Vandor & Admin")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    @GetMapping("{vendorId}")
    public VendorResponse findByVendor(@PathVariable Long vendorId) {
        return vendorService.findByVendor(vendorId);
    }

    @Operation(summary = "Delete vendor by id", description = "Removing vendor profile with id by Vendor & Admin ")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    @DeleteMapping("{vendorId}")
    public SimpleResponse delete(@PathVariable Long vendorId) {
        return vendorService.deleteByVendorId(vendorId);
    }

    @Operation(summary = "Get all books by vendor", description = "Can be obtained using vendor id " +
            "all books, in favorites, in the basket, sold out with discounts in processing and rejected")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'ADMIN')")
    @GetMapping("{vendorId}/books")
    public Page<ABookVendorResponse> findABookVendor(@PathVariable Long vendorId,
                                                     @RequestParam AboutBooks aboutBooks,
                                                     @RequestParam(required = false, defaultValue = "1") int page,
                                                     @RequestParam(required = false, defaultValue = "16") int size) {
        return vendorService.findABookVendor(vendorId, aboutBooks, page, size);
    }

}
