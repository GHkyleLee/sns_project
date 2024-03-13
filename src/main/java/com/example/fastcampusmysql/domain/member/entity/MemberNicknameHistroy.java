package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class MemberNicknameHistroy {

    /*
       데이터 정규화시 실무에서 -> 이 정규화 대상이 하기의 고민해야한다
                                1. 데이터의 최신성을 보장해야하는가?
                                2. 과거의 기록을 남겨야하는가?
     */
    final private Long id;

    final private Long memberId;

    final private String nickname;

    final private LocalDateTime createdAt;

    @Builder
    public MemberNicknameHistroy(Long id, Long memberId, String nickname, LocalDateTime createdAt) {

        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

}
