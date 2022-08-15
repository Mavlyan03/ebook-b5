package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public VendorResponse(User vendor) {
        this.id = vendor.getId();
        this.firstName = vendor.getFirstName();
        this.lastName = vendor.getLastName();
        this.phoneNumber = vendor.getPhoneNumber();
        this.email = vendor.getEmail();
    }
}
