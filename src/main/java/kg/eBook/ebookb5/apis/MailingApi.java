package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.services.MailingService;
import kg.eBook.ebookb5.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mailing")
@RequiredArgsConstructor
public class MailingApi {

    private final MailingService emailService;
    private final UserService personService;
    private String message;

//    @PostMapping("") //TODO
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<HttpStatus> sendMail() {
//        message = "Congratulations! You were registered to mailing list";
//        Person byId = personService.findById(userId);
//        emailService.send(byId.getEmail(), message);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

}
