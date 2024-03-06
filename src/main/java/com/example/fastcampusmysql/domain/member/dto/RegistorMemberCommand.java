package com.example.fastcampusmysql.domain.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegistorMemberCommand(
        String email,
        String nickname,
        LocalDate birthday,

        LocalDateTime createdAt
) {


}
