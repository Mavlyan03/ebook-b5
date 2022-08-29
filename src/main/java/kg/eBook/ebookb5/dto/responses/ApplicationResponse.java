package kg.eBook.ebookb5.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {

    private int unwatched;

    Page<AdminApplicationsResponse> getApplications;
}
