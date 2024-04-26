package com.example.fastcampusmysql.domain.follow.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Follow {
    final private Long id;

    final private Long formMemberId;

    final private Long toMemberId;

    final private LocalDateTime createdAt;

    public Follow(Long id, Long formMemberId, Long toMemberId, LocalDateTime createdAt) {
        this.id = id;
        this.formMemberId = Objects.requireNonNull(formMemberId);
        this.toMemberId = Objects.requireNonNull(toMemberId);
        this.createdAt = createdAt == null ? LocalDateTime.now() :createdAt;
    }
}
