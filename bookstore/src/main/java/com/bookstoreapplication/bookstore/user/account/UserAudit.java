package com.bookstoreapplication.bookstore.user.account;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
class UserAudit implements Serializable {
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
