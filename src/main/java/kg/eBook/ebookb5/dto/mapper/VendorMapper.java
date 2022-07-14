package kg.eBook.ebookb5.dto.mapper;

import kg.eBook.ebookb5.dto.requests.VendorRegisterRequest;
import kg.eBook.ebookb5.dto.responses.VendorRegisterResponse;
import kg.eBook.ebookb5.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorMapper {

    public VendorRegisterResponse VendorToDto(Person person) {
        VendorRegisterResponse vendorRegisterResponse = new VendorRegisterResponse();
        vendorRegisterResponse.setId(person.getId());
        vendorRegisterResponse.setFirstName(person.getFirstName());
        vendorRegisterResponse.setLastName(person.getLastName());
        vendorRegisterResponse.setEmail(person.getEmail());
        vendorRegisterResponse.setPhoneNumber(person.getPhoneNumber());
        vendorRegisterResponse.setPassword(person.getPassword());
        return vendorRegisterResponse;
    }

    public Person DtoToVendor(VendorRegisterRequest vendorRegisterRequest) {
        Person vendor = new Person();
        vendor.setFirstName(vendorRegisterRequest.getFirstName());
        vendor.setEmail(vendorRegisterRequest.getEmail());
        vendor.setPassword(vendorRegisterRequest.getPassword());
        return vendor;
    }

}
