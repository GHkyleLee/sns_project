package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreateFolloweMemberUsecase;
import com.example.fastcampusmysql.application.usecase.GetFollowingMembersUsecase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    final private CreateFolloweMemberUsecase createFolloweMemberUsecase;

    final private GetFollowingMembersUsecase getFollowingMembersUsecase;

    @PostMapping("/{fromId}/{toId}")
    public void create(@PathVariable Long fromId, @PathVariable Long toId){
        createFolloweMemberUsecase.excute(fromId,toId);
    }
    @GetMapping("members/{fromId}")
    public List<MemberDto> create(@PathVariable Long fromId){

        return  getFollowingMembersUsecase.excute(fromId);
    }


}
