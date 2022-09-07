package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.GenreResponse;
import kg.eBook.ebookb5.models.Genre;
import kg.eBook.ebookb5.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<GenreResponse> findAll() {

        List<GenreResponse> genreResponses = new ArrayList<>();
        List<Genre> genres = genreRepository.findAll();
        for (Genre genre : genres) {
            genreResponses.add(new GenreResponse(genre, genreRepository.quantityOfBook(genre.getId()).orElse(null)));
        }
        return genreResponses;
    }
}
