package com.bookstoreapplication.bookstore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserJpaRepository extends UserRepository, JpaRepository<User, Long> {

}
