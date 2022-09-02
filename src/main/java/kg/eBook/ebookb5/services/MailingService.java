package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.MailNewBookRequest;
import kg.eBook.ebookb5.dto.requests.RequestMailingList;
import kg.eBook.ebookb5.models.MailingList;
import kg.eBook.ebookb5.repositories.MailingListRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@AllArgsConstructor
public class MailingService {

    private final JavaMailSender javaMailSender;
    private final MailingListRepository mailingListRepository;
    private final Logger logger = LoggerFactory.getLogger(MailingService.class);

    public void sendSignUpMessage(RequestMailingList requestMailingList) {

        logger.info("Send sign up message ...");
        MailingList mailingList = new MailingList(requestMailingList.getEmail());
        mailingListRepository.save(mailingList);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("ebook.peaksoft@gmail.com");
        simpleMailMessage.setSubject("PEAKSOFT");
        simpleMailMessage.setTo(mailingList.getEmail());
        simpleMailMessage.setText("Поздравляем, Вы подписаны на рассылку!");
        this.javaMailSender.send(simpleMailMessage);

        logger.info("User subscribed to the newsletter!");
    }


    public void sendNewBookMessage(MailNewBookRequest mailNewBookRequest) {

        logger.info("Send new book message ...");
        List<MailingList> emailLists = mailingListRepository.findAll();
        for(MailingList i: emailLists) {
            sendHTMLMessage(i.getEmail(), mailNewBookRequest.createHtmlMessage());
        }

        logger.info("Sent message about new book");
    }

    @Async
    public void sendHTMLMessage(String email, String htmlMessage) {

        logger.info("Send HTML message ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            logger.warn("Sending HTML message may be an error");
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    "UTF-8");

            helper.setFrom("ebook.peaksoft@gmail.com");
            helper.setTo(email);
            helper.setText(htmlMessage,true);
            javaMailSender.send(mimeMessage);

            logger.info("HTML message sent");
        } catch (MessagingException e) {

            logger.trace("Html message in the progress" + e);
            e.printStackTrace();
        }
    }
}
