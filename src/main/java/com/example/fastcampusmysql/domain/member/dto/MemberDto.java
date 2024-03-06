package com.example.fastcampusmysql.domain.member.dto;

import java.time.LocalDate;

public record MemberDto(
        long id,
        String email,
        String nickname,
        LocalDate birthday
) {
}
