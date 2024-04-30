package com.example.fastcampusmysql.domain.follow.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class FollowRepository {

    final private JdbcTemplate JdbcTemplate;

    static final private String TABLE = "follow";

    public Follow save(Follow follow){
        /*
            follow 저장
         */

        if(follow.getId() == null){
            return insert(follow);
        }

        throw new UnsupportedOperationException("Follow는 갱신을 지원하지 않습니다.");
    }

    private Follow insert(Follow follow){
        /*
         팔로우 추가
         */
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(JdbcTemplate)
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(follow);

        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Follow
                .builder()
                .id(id)
                .fromMemberId(follow.getFromMemberId())
                .toMemberId(follow.getToMemberId())
                .createdAt(follow.getCreatedAt())
                .build();

    }
}
