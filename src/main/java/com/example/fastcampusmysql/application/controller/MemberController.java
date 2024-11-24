package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.dto.RegistorMemberCommand;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@OpenAPIDefinition
@RestController
public class MemberController {

    private final MemberWriteService memberWriteService;

    private final MemberReadService memberReadService;

    @PostMapping("/members")
    public MemberDto register(@RequestBody RegistorMemberCommand command){
        /*
        *  멤버 등록
        * */

        var member =  memberWriteService.register(command);

        return memberReadService.toDto(member);
    }

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable long id){
        /*
        *  멤버 조회
        * */
        return memberReadService.getMember(id);

    }

    @PostMapping("/{id}/name")
    public MemberDto changeNickName(@PathVariable Long id, @RequestBody String nickname) {
        /*
        *  멤버 닉네임 변경
        * */
        memberWriteService.changNickname(id, nickname);
        return memberReadService.getMember(id);
    }
    @GetMapping("{memberId}/member-historys")
    public List<MemberNicknameHistoryDto> getNicknameHistory (@PathVariable Long memberId ){
        /*
        *  멤버 닉네임 히스토리 조회
        * */
        return memberReadService.getNicknameHistory(memberId);
    }
}

