package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.models.Person;
import kg.eBook.ebookb5.services.EmailService;
import kg.eBook.ebookb5.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mailing")
@RequiredArgsConstructor
public class MailingApi {

    private final EmailService emailService;
    private final PersonService personService;
    private String message;

    @PostMapping("/{userId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<HttpStatus> sendMail(@PathVariable Long userId) {
        message = "Congratulations! You were registered to mailing list";
        Person byId = personService.findById(userId);
        emailService.send(byId.getEmail(), message);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
