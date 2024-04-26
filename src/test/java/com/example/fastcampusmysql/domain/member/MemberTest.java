package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.utill.MemberFixtrueFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @Test
    void name() {
    }

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    public void testChangeName() {

        var member = MemberFixtrueFactory.create();
        var expected = "pnu";
        System.out.println(member.getNickname());
        member.changeNickname(expected);
        System.out.println(member.getNickname());

        Assertions.assertEquals(expected, member.getNickname());

/*        LongStream.range(0, 10)
                .mapToObj(MemberFixtrueFactory::create)
                .forEach(member -> {
                    System.out.println(member.getNickname());
                });*/
        // object mother
    }

    @DisplayName("회원의 닉네임은 10자를 초과할수 없다")
    @Test
    public void testNicknameMaxLength(){
        var member = MemberFixtrueFactory.create();
        var overMaxLengthName = "1234567890s";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> member.changeNickname(overMaxLengthName)
        );
    }
}
