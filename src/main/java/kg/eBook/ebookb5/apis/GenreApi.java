package kg.eBook.ebookb5.apis;

import kg.eBook.ebookb5.dto.responses.GenreResponse;
import kg.eBook.ebookb5.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/genres")
public class GenreApi {

    private GenreService genreService;

    @GetMapping
    public List<GenreResponse> findAllGenres() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public GenreResponse findById(@PathVariable Long id) {
        return genreService.findById(id);
    }
}
