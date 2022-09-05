package kg.eBook.ebookb5.dto.responses.vendorBooksResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorBookResponse {

    private int totalElement;

    private List<ABookVendorResponse> bookVendorResponses;
}
