package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.core.User;
import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllBy(Pageable page);

    Optional<User> findByEmail(UserEmail email);

}
