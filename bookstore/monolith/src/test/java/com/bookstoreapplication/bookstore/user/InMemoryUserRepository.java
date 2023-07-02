package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;
import org.springframework.data.domain.Pageable;

import java.util.*;

class InMemoryUserRepository implements UserRepository{

    private final Map<Long, User> userDatabase = new HashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userDatabase.values());
    }

    @Override
    public List<User> findAllBy(Pageable page) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(UserEmail email) {
        return userDatabase.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public User save(User user) {
        userDatabase.put(user.getUserId(), user);
        return user;
    }

    @Override
    public void delete(User userToDelete) {
        userDatabase.remove(userToDelete.getUserId());
    }

    @Override
    public void deleteAll() {
        userDatabase.clear();
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return Optional.ofNullable(userDatabase.get(userId));
    }
}
