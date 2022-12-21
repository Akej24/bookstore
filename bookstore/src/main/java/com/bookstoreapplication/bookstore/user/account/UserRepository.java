package com.bookstoreapplication.bookstore.user.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<UserDatabaseModel, Long> {
    Optional<UserDatabaseModel> findByEmail(String email);
}
