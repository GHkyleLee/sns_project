package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegistorMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@OpenAPIDefinition
@RestController
public class MemberController {

    private final MemberWriteService memberWriteService;

    private final MemberReadService memberReadService;

    @PostMapping("/members")
    public MemberDto register(@RequestBody RegistorMemberCommand command){

        var member =  memberWriteService.create(command);

        return memberReadService.toDto(member);
    }

    @GetMapping("/members/{id}®")
    public MemberDto getMember(@PathVariable long id){
        return memberReadService.getMember(id);

    }
}

