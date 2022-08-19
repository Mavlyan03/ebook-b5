package kg.eBook.ebookb5.dto.responses;

import kg.eBook.ebookb5.enums.SearchType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResponse {
    private Long id;

    private String search;

    private SearchType searchType;

    public SearchResponse(Long id, String search, SearchType searchType) {
        this.id = id;
        this.search = search;
        this.searchType = searchType;
    }

    public SearchResponse() {
    }


}
