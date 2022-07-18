package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReposiroty extends JpaRepository<User, Long> {
}
