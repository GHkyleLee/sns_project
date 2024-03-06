package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.member.dto.RegistorMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@OpenAPIDefinition
@RestController
public class MemberController {

    private final MemberWriteService memberWriteService;

    @PostMapping("/members")
    public Member register(@RequestBody RegistorMemberCommand command){
        return memberWriteService.create(command);
    }

}

