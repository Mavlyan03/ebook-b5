package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.models.MailingList;
import kg.eBook.ebookb5.repositories.MailingListRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MailingService {

    private final JavaMailSender javaMailSender;
    private final MailingListRepository mailingListRepository;
    private final Logger logger = LoggerFactory.getLogger(MailingService.class);

    public void send(String to, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("timur.abdivaitov@gmail.com");
        simpleMailMessage.setSubject("PEAKSOFT");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(message);
        this.javaMailSender.send(simpleMailMessage);
    }

    public List<MailingList> collectAndSendEmails(MailingList email) {
        mailingListRepository.save(email);
        return mailingListRepository.findAll();
    }


//    public void sendHtmlMessage(String to, String htmlMessage) throws MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
//                mimeMessage,
//                true,
//                "UTF-8"
//        );
//        mimeMessageHelper.setTo(to);
//        mimeMessageHelper.setSubject("Email");
//        mimeMessageHelper.setText(htmlMessage, true);
//        javaMailSender.send(mimeMessage);
//    }

}