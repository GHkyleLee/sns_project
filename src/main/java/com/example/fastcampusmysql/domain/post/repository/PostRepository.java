package com.example.fastcampusmysql.domain.post.repository;


import com.example.fastcampusmysql.utill.PageHelper;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

@RequiredArgsConstructor
@Repository
public class PostRepository {

    final static String TABLE = "POST";

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    final static private RowMapper<Post> ROW_MAPPER = (ResultSet resultSet, int rowNum)
            -> Post.builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .contents(resultSet.getString("contents"))
            .createdDate(resultSet.getObject("createdDate", LocalDate.class))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();
    final static private RowMapper<DailyPostCount> DAILY_POST_COUNT_ROW_MAPPER = (ResultSet resultSet, int rowNum)
            -> new DailyPostCount(
                    resultSet.getLong("memberId"),
                    resultSet.getObject("createdDate",LocalDate.class),
                    resultSet.getLong("count")
                );

    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
        var sql = String.format("""
                SELECT createdDate, memberId, count(id) as count
                FROM %s
                WHERE memberId = :memberId
                  AND createdDate BETWEEN :firstDate and :lastDate
                GROUP BY memberId, createdDate
                """,TABLE);
        var params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_ROW_MAPPER);
    }




    public Page<Post> findAllByMemberId(Long memberId, Pageable pageable) {

        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId
                ORDER BY %s
                LIMIT :size
                OFFSET :offset
                """, TABLE, PageHelper.orderBy(pageable.getSort()));

        var posts = namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);


        return new PageImpl<>(posts, pageable, getCount(memberId));
    }

    private Long getCount(Long memberId){
        var sql = String.format("""
                SELECT count(id)
                FROM %s
                WHERE memberId = :memberId
                """,TABLE);
        var params = new MapSqlParameterSource().addValue("memberId",memberId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }
    public List<Post> findAllMemberIdAndOrderByIdDesc(Long memberId, int size){
        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId
                ORDER BY id desc
                LIMIT :size
                """,TABLE);
        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql,params,ROW_MAPPER);
    }

    public List<Post> findAllByLessThanAndMemberIdAndOrderByIdDesc(Long id, Long memberId, int size){
        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId and id < :id
                ORDER BY id desc
                LIMIT :size
                """,TABLE);
        var params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("memberId", memberId)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql,params,ROW_MAPPER);
    }

    public Post save(Post post) {
        if(post.getId() == null){
            return insert(post);
        }
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");

    }

    public void bulkInsert(List<Post> posts){
        var sql = String.format("""
               INSERT INTO %s (memberId, contents, createdDate, createdAt)
               VALUES (:memberId, :contents, :createdDate, :createdAt)
               """,TABLE);
        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private Post insert(Post post) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }

}
