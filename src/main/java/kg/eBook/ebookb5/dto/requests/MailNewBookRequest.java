package kg.eBook.ebookb5.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailNewBookRequest {

    private String mainImage;
    private String name;
    private int price;

    public String createHtmlMessage() {
        return String.format(
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>send email message</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<img src=\"%s\">\n" +
                        "<br>\n" +
                        "<h1>%s</h1>\n" +
                        "<br>\n" +
                        "<p>%s</p>\n" +
                        "<br>\n" +
                        "</body>\n" +
                        "</html>",
                mainImage,
                name,
                price
        );
    }

}
