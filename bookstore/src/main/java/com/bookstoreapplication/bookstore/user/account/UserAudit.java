package com.bookstoreapplication.bookstore.user.account;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
