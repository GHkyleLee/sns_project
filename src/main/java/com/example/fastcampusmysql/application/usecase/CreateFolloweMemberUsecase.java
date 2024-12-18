package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFolloweMemberUsecase {
    /*
    *  팔로우 등록
    * */
    final private MemberReadService memberReadService;

    final private FollowWriteService followWriteService;

    public void excute(Long fromMemberId, Long toMemberId){
        /*
            1.입력받은 memberId로 회원조회
            2.FollowWriteService.create()
         */
        var fromMember = memberReadService.getMember(fromMemberId);

        var toMember = memberReadService.getMember(toMemberId);

        followWriteService.crate(fromMember,toMember);

    }
}
