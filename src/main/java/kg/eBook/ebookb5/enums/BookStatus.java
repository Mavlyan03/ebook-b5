package kg.eBook.ebookb5.enums;

public enum BookStatus {

    IN_PROCESSING("В обработке"),
    ACCEPTED("Принятый"),
    REJECTED("Отклоненный");

    private String status;

    BookStatus(String status) {
        this.status = status;
    }

}
