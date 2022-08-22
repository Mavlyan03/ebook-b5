package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.models.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VendorResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private Integer quantityOfBooks;

    private LocalDate dateOfRegistration;

    public VendorResponse(User vendor) {
        this.id = vendor.getId();
        this.firstName = vendor.getFirstName();
        this.lastName = vendor.getLastName();
        this.phoneNumber = vendor.getPhoneNumber();
        this.email = vendor.getEmail();
        this.quantityOfBooks = vendor.getBooks().size();
        this.dateOfRegistration = vendor.getCreatedAt();
    }

    public static List<VendorResponse> viewVendors(List<User> vendors) {
        List<VendorResponse> vendorResponses = new ArrayList<>();
        for (User vendor : vendors) {
            vendorResponses.add(new VendorResponse(vendor));
        }
        return vendorResponses;
    }
}
