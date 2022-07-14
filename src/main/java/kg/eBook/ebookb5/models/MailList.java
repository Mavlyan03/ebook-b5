package kg.eBook.ebookb5.models;

import javax.persistence.*;

@Entity
public class MailList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "mailList_gen", sequenceName = "mailList_seq", allocationSize = 1)
    private Long id;

    private String email;
}
