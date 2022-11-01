package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.requests.RequestMailingList;
import kg.eBook.ebookb5.services.MailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mailing")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Mailing API", description = "Mailing endpoints")
public class MailingApi {

    private final MailingService emailService;

    @Operation(summary = "Send mailing", description = "Подписка на рассылку")
    @PreAuthorize("permitAll()")
    @PostMapping("signup")
    public ResponseEntity<String> sendMail(@RequestBody RequestMailingList requestMailingList) {
        emailService.sendSignUpMessage(requestMailingList);
        return ResponseEntity.ok("Письмо отправлено");
    }

}
