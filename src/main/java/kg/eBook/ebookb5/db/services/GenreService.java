package kg.eBook.ebookb5.db.services;

import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.Role;
import kg.eBook.ebookb5.dto.responses.GenreResponse;
import kg.eBook.ebookb5.db.models.Genre;
import kg.eBook.ebookb5.db.models.User;
import kg.eBook.ebookb5.db.repositories.GenreRepository;
import kg.eBook.ebookb5.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    public List<GenreResponse> findAll(Authentication authentication) {
        List<GenreResponse> genreResponses = new ArrayList<>();
        List<Genre> genres = genreRepository.findAll();
        if (authentication != null) {
            User user = userRepository.findByEmail(authentication.getName()).get();
            if (user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.VENDOR)) {
                for (Genre genre : genres) {
                    genreResponses.add(new GenreResponse(genre, genreRepository.quantityOfBook(genre.getId()).orElse(null)));
                }
            } else if (user.getRole().equals(Role.USER)) {
                for (Genre genre : genres) {
                    genreResponses.add(new GenreResponse(genre, genreRepository.quantityOfBookACCEPTED(BookStatus.ACCEPTED, genre.getId()).orElse(null)));
                }
            }
        } else {
            for (Genre genre : genres) {
                genreResponses.add(new GenreResponse(genre, genreRepository.quantityOfBookACCEPTED(BookStatus.ACCEPTED, genre.getId()).orElse(null)));
            }
        }
        return genreResponses;
    }

}
