package com.bookstoreapplication.bookstore.book;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
class BookAudit implements Serializable {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void preCreate() {
        this.createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

}
