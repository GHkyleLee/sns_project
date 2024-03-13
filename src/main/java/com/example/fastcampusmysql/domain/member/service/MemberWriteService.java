package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegistorMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class MemberWriteService {

    final private MemberRepository memberRepository;

    public Member register(RegistorMemberCommand command){
        /*
            목표 - 회원정보(이메일, 닉네임, 생년월일)를 등록한다.
            닉네임은 10자를 넘길수 없다.
            파라미터 - memberRegisterCommand
            vol member = Member.of(memberRegisterCommand)
            memberRepository save()
         */

        var member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();

        return memberRepository.save(member);

    }

    public void changNickname(Long memberId, String nickName) {
        /*
            1. 회원의 이름을 변경한다
            2. 변경내역을 저장한다.
         */

        var member = memberRepository.findbyId(memberId).orElseThrow();
        member.changeNickname(nickName);
        memberRepository.save(member);
        // TODO: 변경내역 히스토리르 저장한다.
    }

}
