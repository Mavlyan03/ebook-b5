package kg.eBook.ebookb5.enums;

public enum SortBy {

    BESTSELLER("Бестселлеры"),
    NEW("Новинки");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
