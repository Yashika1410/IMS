package com.example.inventory.ims.entities;



import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(name="created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    @LastModifiedDate
    protected LocalDateTime updatedAt;
    @Column(name = "created_by_user_id")
    protected Long createdByUserId;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    protected Boolean isDeleted =false;
}
