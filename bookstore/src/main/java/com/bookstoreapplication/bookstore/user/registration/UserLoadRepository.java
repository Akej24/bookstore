package com.bookstoreapplication.bookstore.user.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserLoadRepository extends JpaRepository<UserRegistrationModel, Long> {
    Optional<UserRegistrationModel> findByEmail(String email);

}
