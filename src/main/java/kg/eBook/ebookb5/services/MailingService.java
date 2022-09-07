package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.MailNewBookRequest;
import kg.eBook.ebookb5.dto.requests.RequestMailingList;
import kg.eBook.ebookb5.models.MailingList;
import kg.eBook.ebookb5.repositories.MailingListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MailingService {

    private final JavaMailSender javaMailSender;
    private final MailingListRepository mailingListRepository;

    public void sendSignUpMessage(RequestMailingList requestMailingList) {

        MailingList mailingList = new MailingList(requestMailingList.getEmail());
        mailingListRepository.save(mailingList);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("ebook.peaksoft@gmail.com");
        simpleMailMessage.setSubject("PEAKSOFT");
        simpleMailMessage.setTo(mailingList.getEmail());
        simpleMailMessage.setText("Поздравляем, Вы подписаны на рассылку!");
        this.javaMailSender.send(simpleMailMessage);

        log.info("User subscribed to the newsletter!");
    }


    public void sendNewBookMessage(MailNewBookRequest mailNewBookRequest) {

        List<MailingList> emailLists = mailingListRepository.findAll();
        for(MailingList i: emailLists) {
            sendHTMLMessage(i.getEmail(), mailNewBookRequest.createHtmlMessage());
        }
    }

    @Async
    public void sendHTMLMessage(String email, String htmlMessage) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            log.warn("Sending HTML message may be an error");
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    "UTF-8");

            helper.setFrom("ebook.peaksoft@gmail.com");
            helper.setTo(email);
            helper.setText(htmlMessage,true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
