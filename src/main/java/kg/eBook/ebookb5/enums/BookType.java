package kg.eBook.ebookb5.enums;

public enum BookType {
    PAPER_BOOK("Бумажная"),
    AUDIO_BOOK("Аудиокнига"),
    ELECTRONIC_BOOK("Электронная книга");

    private String book;

    BookType(String book) {
        this.book = book;
    }
}
