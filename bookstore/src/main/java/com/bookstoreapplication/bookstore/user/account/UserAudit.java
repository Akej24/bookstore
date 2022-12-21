package com.bookstoreapplication.bookstore.user.account;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Embeddable
class UserAudit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime lastLogin;

    @PrePersist
    void preCreate() {
        this.createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

    @PostUpdate
    void preLogin() {
        this.lastLogin = LocalDateTime.now();
    }
}
