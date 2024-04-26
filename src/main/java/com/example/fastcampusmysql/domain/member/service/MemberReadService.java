package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistroy;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {

    final private MemberRepository memberRepository;
    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(long id) {
        var member = memberRepository.findbyId(id).orElseThrow();

        return toDto(member);
    }

    public List<MemberNicknameHistoryDto> getNicknameHistory(long memberId) {

        return memberNicknameHistoryRepository
                .findAllbyMemberId(memberId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

    public MemberNicknameHistoryDto toDto(MemberNicknameHistroy histroy){
        return new MemberNicknameHistoryDto(
                histroy.getId(),
                histroy.getMemberId(),
                histroy.getNickname(),
                histroy.getCreatedAt()
        );
    }
}
