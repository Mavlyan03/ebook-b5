package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import kg.eBook.ebookb5.dto.responses.GenreResponse;
import kg.eBook.ebookb5.services.GenreService;
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
public class GenreApi {

    private final GenreService genreService;

    @GetMapping
    @Operation(summary = "find all genres", description = "how many books in each genre")
    public List<GenreResponse> findAllGenres(Authentication authentication) {
        return genreService.findAll(authentication);
    }
}
