package com.bookstoreapplication.bookstore.user.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDatabaseModel, Long> {
    Optional<UserDatabaseModel> findByEmail(String email);

}
