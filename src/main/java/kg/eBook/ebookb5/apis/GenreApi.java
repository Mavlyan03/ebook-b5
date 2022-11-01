package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.dto.responses.GenreResponse;
import kg.eBook.ebookb5.db.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/genres")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Genre API", description = "Book genre endpoints")
public class GenreApi {

    private final GenreService genreService;

    @Operation(summary = "Get all genres", description = "Get how many books in each genre")
    @GetMapping
    public List<GenreResponse> findAllGenres(Authentication authentication) {
        return genreService.findAll(authentication);
    }
}
