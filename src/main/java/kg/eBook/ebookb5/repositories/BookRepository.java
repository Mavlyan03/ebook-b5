package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.models.Book;
import kg.eBook.ebookb5.models.PurchasedUserBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
