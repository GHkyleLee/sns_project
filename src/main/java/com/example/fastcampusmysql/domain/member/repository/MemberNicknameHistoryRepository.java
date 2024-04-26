package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistroy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberNicknameHistoryRepository {

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final private String TABLE = "membernicknamehistory";
    static final RowMapper<MemberNicknameHistroy> rowMapper = (ResultSet resultSet, int romNum) -> MemberNicknameHistroy
            .builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .nickname(resultSet.getString("nickname"))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public List<MemberNicknameHistroy> findAllbyMemberId(long memberId){
        var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId",TABLE);
        var params = new MapSqlParameterSource().addValue("memberId",memberId);
        return  namedParameterJdbcTemplate.query(sql,params,rowMapper);
    }

    public MemberNicknameHistroy save(MemberNicknameHistroy histroy){
        /*
            member id를 보고 갱신 또는 삽입을 정함
            반환값은 id를 담아서 반환한다.
         */

        if(histroy.getId() == null){
            return insert(histroy);
        }
        
        throw new UnsupportedOperationException("MemberNicknameHistory는 갱신을 지원하지 않습니다");
    }

    private MemberNicknameHistroy insert(MemberNicknameHistroy histroy){
        /*
         멤버 생성
         */
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(histroy);

        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return MemberNicknameHistroy
                .builder()
                .id(id)
                .memberId(histroy.getMemberId())
                .nickname(histroy.getNickname())
                .createdAt(histroy.getCreatedAt())
                .build();

    }

}
