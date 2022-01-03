package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //User findByEmail(String email);
    Optional<User> findByEmail(String email);
    
    /* bbogdasha
    @Query(value = "select * from users where email =?1", nativeQuery = true)
    User getUserByEmail(String email);
    */

}
