package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.requests.RequestMailingList;
import kg.eBook.ebookb5.services.MailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mailing")
@RequiredArgsConstructor
public class MailingApi {

    private final MailingService emailService;

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    @Operation(description = "Подписка на рассылку")
    public ResponseEntity<String> sendMail(@RequestBody RequestMailingList requestMailingList) {
        emailService.sendSignUpMessage(requestMailingList);
        return ResponseEntity.ok("Письмо отправлено");
    }

//    @PostMapping("/send-to-all-emails")
//    @PreAuthorize("hasAnyAuthority('VENDOR', 'USER', 'ADMIN')")
//    @Operation(description = "Отправка почты всем подписанным пользователям о новой книге")
//    public ResponseEntity<String> sendToAllEmails(@RequestBody ) {
//        emailService.sendNewBookMessage();
//        return ResponseEntity.ok("Почта о добавлении новой книги отправлено пользователям");
//    }

}
