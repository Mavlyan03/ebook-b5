package kg.eBook.ebookb5.repositories;

import kg.eBook.ebookb5.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

//    @Modifying
//    @Query("delete from User u where u.id = :id ")
//    void deleteVendor(@Param("id") Long id);

    @Query("select u from User u where u.role = 'USER'")
    List<User> findAllUsers();
}