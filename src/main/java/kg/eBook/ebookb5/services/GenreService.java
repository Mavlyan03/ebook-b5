package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.responses.GenreResponse;
import kg.eBook.ebookb5.exceptions.NotFoundException;
import kg.eBook.ebookb5.models.Genre;
import kg.eBook.ebookb5.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final Logger logger = LoggerFactory.getLogger(GenreService.class);

    public List<GenreResponse> findAll() {

        logger.info("Find all genres ...");
        List<GenreResponse> genreResponses = new ArrayList<>();
        List<Genre> genres = genreRepository.findAll();
        for (Genre genre : genres) {
            genreResponses.add(new GenreResponse(genre, genreRepository.quantityOfBook(genre.getId()).orElse(null)));
        }
        logger.info("All genres are displayed for selection");
        return genreResponses;
    }
}
