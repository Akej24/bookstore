package com.bookstoreapplication.bookstore.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user")
    List<User> findAllUsers(Pageable page);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

}
