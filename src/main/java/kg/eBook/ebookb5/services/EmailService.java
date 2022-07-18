package kg.eBook.ebookb5.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void send(String to, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("timur.abdivaitov@gmail.com");
        simpleMailMessage.setSubject("PEAKSOFT");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(message);
        this.javaMailSender.send(simpleMailMessage);
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
