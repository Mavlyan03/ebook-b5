package kg.eBook.ebookb5.enums;

public enum TypeOfBook {
    PAPER_BOOK("Бумажная"),
    AUDIO_BOOK("Аудиокнига"),
    ELECTRONIC_BOOK("Электронная книга");

    private String book;

    TypeOfBook(String book) {
        this.book = book;
    }
}
