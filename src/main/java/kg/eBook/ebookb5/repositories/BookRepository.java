package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.models.Book;
import org.springframework.data.domain.Pageable;
import kg.eBook.ebookb5.models.PurchasedUserBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByName(String name);

    @Query("select new kg.eBook.ebookb5.dto.responses.BookResponse(" +
            " b.id, b.mainImage, b.name, b.author, b.price, b.bookType)" +
            " from Book b where ((b.genre.id in (:genres) or :genres is null) AND "  +
            " (:bookType is null or b.bookType = :bookType) AND "+
            " ((b.price between :priceFrom and :priceTo) OR (:priceFrom is null or :priceTo is null)) and "+
            " (:languages is null or b.language in (:languages)) and "+
            "  (upper(b.genre.name) like upper(concat('%', :search, '%')) "+
            " or :search = 'all' or :search is null)) " +
            " order by case when :sortBy = 'Новинки' then b.isNew else case when :sortBy = 'Бестселлеры' " +
            " then b.bestseller else true end end desc ")
    List<BookResponse> customFindAll(List<Long> genres,
                                     BookType bookType,
                                     Integer priceFrom,
                                     Integer priceTo,
                                     List<Language>languages,
                                     String search,
                                     String sortBy,
                                     Pageable pageable
    );

}

