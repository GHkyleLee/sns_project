package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    static final private String TABLE = "member";

    static final RowMapper<Member> rowMapper = (ResultSet resultSet, int romNum) -> Member
            .builder()
            .id(resultSet.getLong("id"))
            .email(resultSet.getString("email"))
            .nickname(resultSet.getString("nickname"))
            .birthday(resultSet.getObject("birthday", LocalDate.class))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public Optional<Member> findById(long id){
        /*
            select
                id
            from member
            where id = id
         */

        var sql = String.format("SELECT * FROM %s WHERE ID = :id",TABLE);
        var params = new MapSqlParameterSource()
                .addValue("id",id);
        List<Member> members = namedParameterJdbcTemplate.query(sql, params, rowMapper);

        Member nullableMember = DataAccessUtils.singleResult(members);
        return Optional.ofNullable(nullableMember);
    }


    public List<Member> findAllByIdIn(List<Long> ids) {
        if(ids.isEmpty())
            return List.of();
        var sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
        var params = new MapSqlParameterSource().addValue("ids", ids);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    public Member save(Member member){
        /*
            member id를 보고 갱신 또는 삽입을 정함
            반환값은 id를 담아서 반환한다.
         */

        if(member.getId() == null){
            return insert(member);
        }

        return update(member);
    }

    private Member insert(Member member){
        /*
         멤버 생성
         */
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);

        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member
                .builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();

    }

    private Member update(Member member){
        var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE);
        SqlParameterSource parmas = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, parmas);
        return member;

    }
}
