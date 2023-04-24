package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

interface UserRepository {

    List<User> findAllBy(Pageable page);

    Optional<User> findByEmail(UserEmail email);

    User save(User user);

    void delete(User userToDelete);

    void deleteAll();

    Optional<User> findByUserId_UserId(Long userId);

}
