package com.bookstoreapplication.bookstore.auth;

import com.bookstoreapplication.bookstore.user.value_objects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface SecuredUserRepository extends JpaRepository<SecuredUser, UserId> {

    Optional<SecuredUser> findByUserEmailEmail(String email);

}
