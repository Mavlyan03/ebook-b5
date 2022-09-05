package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.dto.responses.AdminApplicationsResponse;
import kg.eBook.ebookb5.dto.responses.AdminBooksResponse;
import kg.eBook.ebookb5.dto.responses.BookResponse;
import kg.eBook.ebookb5.dto.responses.ABookVendorResponse;
import kg.eBook.ebookb5.enums.BookStatus;
import kg.eBook.ebookb5.enums.BookType;
import kg.eBook.ebookb5.enums.Language;
import kg.eBook.ebookb5.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByName(String name);

    @Query("select new kg.eBook.ebookb5.dto.responses.BookResponse(" +
            " b.id, b.mainImage, b.name, b.author, b.price, b.bookType)" +
            " from Book b where b.bookStatus='ACCEPTED' AND ((b.genre.id in (:genres) or :genres is null) AND " +
            " (:bookType is null or b.bookType = :bookType) AND " +
            " ((b.price between :priceFrom and :priceTo) OR (:priceFrom is null or :priceTo is null)) and " +
            " (:languages is null or b.language in (:languages)) and " +
            "  (upper(b.genre.name) like upper(concat('%', :search, '%')) " +
            " or :search = 'all' or :search is null)) " +
            " order by case when :sortBy = 'Новинки' then b.isNew else case when :sortBy = 'Бестселлеры' " +
            " then b.bestseller else true end end desc ")
    Page<BookResponse> customFindAll(List<Long> genres,
                                     BookType bookType,
                                     Integer priceFrom,
                                     Integer priceTo,
                                     List<Language> languages,
                                     String search,
                                     String sortBy,
                                     Pageable pageable
    );

//    @Query("select kg.eBook.ebookb5.dto.responses.SearchResponse(" +
//            "id, search, searchType) where ")
//    List<SearchResponse> globalSearchBooks(String search);

    @Query("select new kg.eBook.ebookb5.dto.responses.AdminApplicationsResponse( " +
            " b.id, b.mainImage, b.name, b.publishedDate, b.price, b.isEnabled)  " +
            "from Book b " +
            "where b.bookStatus='IN_PROCESSING' " +
            "order by b.isEnabled asc ")
    Page<AdminApplicationsResponse> getApplications(Pageable pageable);

    @Query("select count(b) from Book b where b.bookStatus ='IN_PROCESSING' and b.isEnabled = false ")
    int countOfUnseen();

    @Query("select new kg.eBook.ebookb5.dto.responses.AdminBooksResponse(" +
            "b.id, b.mainImage, b.name, b.publishedDate, b.price, b.bookType) from Book b where " +
            " ((b.genre.id = :genre or :genre is null) and " +
            "(:bookType is null or b.bookType = :bookType)) " +
            "order by b.publishedDate desc")
    Page<AdminBooksResponse> findAllBooks(Long genre,
                                          BookType bookType,
                                          Pageable pageable);

    @Query("select new kg.eBook.ebookb5.dto.responses.ABookVendorResponse(" +
            "b.id, b.name, b.mainImage, b.price, b.publishedDate, b.likes.size, b.bookBasket.size) " +
            "from Book b where b.owner.id = :owner_id ")
    Page<ABookVendorResponse> findBooksByOwnerId(Long owner_id, Pageable pageable);

    @Query("select new kg.eBook.ebookb5.dto.responses.ABookVendorResponse(" +
            "b.id, b.name, b.mainImage, b.price, b.publishedDate, b.likes.size, b.bookBasket.size) " +
            "from Book b where b.owner.id = :owner_id and b.bookStatus = :bookStatus ")
    Page<ABookVendorResponse> findBooksByOwnerIdAndBookStatus(Long owner_id, BookStatus bookStatus, Pageable pageable);

    @Query("select new kg.eBook.ebookb5.dto.responses.ABookVendorResponse(" +
            "b.id, b.name, b.mainImage, b.price, b.publishedDate, b.likes.size, b.bookBasket.size) " +
            "from Book b where b.owner.id = :owner_id and b.discount > 0 ")
    Page<ABookVendorResponse> findBooksByOwnerIdAndDiscountNotNull(Long owner_id, Pageable pageable);

    @Query("select new kg.eBook.ebookb5.dto.responses.ABookVendorResponse(" +
            "b.id, b.name, b.mainImage, b.price, b.publishedDate, b.likes.size, b.bookBasket.size) " +
            "from Book b where b.owner.id = :owner_id and b.bookBasket.size > 0 ")
    Page<ABookVendorResponse> findBooksByOwnerIdAndBookBasketIsNotNull(Long owner_id, Pageable pageable);

    @Query("select new kg.eBook.ebookb5.dto.responses.ABookVendorResponse(" +
            "b.id, b.name, b.mainImage, b.price, b.publishedDate, b.likes.size, b.bookBasket.size) " +
            "from Book b where b.owner.id = :owner_id and b.likes.size > 0 ")
    Page<ABookVendorResponse> findBooksByOwnerIdAndLikesIsNotNull(Long owner_id, Pageable pageable);

    @Query("select new kg.eBook.ebookb5.dto.responses.ABookVendorResponse(" +
            "b.id, b.name, b.mainImage, b.price, b.publishedDate, b.likes.size, b.bookBasket.size) " +
            "from Book b where b.id in (:ids) ")
    Page<ABookVendorResponse> findAllById(List<Long> ids, Pageable pageable);
}

