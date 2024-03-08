package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.utill.MemberFixtrueFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    public void testChangeName(){

        var member = MemberFixtrueFactory.create();
        var expected = "pnu";

        member.changeNickname(expected);

        Assertions.assertEquals(expected, member.getNickname());

/*        IntStream.range(0,10)
                .mapToObj(i-> MemberFixtrueFactory.create())
                .forEach(member -> {
                    System.out.println(member.getNickname());
                });*/
/*        LongStream.range(0,10)
                .mapToObj(MemberFixtrueFactory::create)
                .forEach(member -> {
                    System.out.println(member.getNickname());
                });*/
        // object mother

    }
}
