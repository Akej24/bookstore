package com.bookstoreapplication.bookstore.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface SecuredUserRepository extends JpaRepository<SecuredUser, Long> {

    Optional<SecuredUser> findByUserEmailEmail(String email);

}
