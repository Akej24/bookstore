package com.bookstoreapplication.bookstore.book;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
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
