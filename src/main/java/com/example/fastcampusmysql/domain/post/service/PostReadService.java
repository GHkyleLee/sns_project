package com.example.fastcampusmysql.domain.post.service;


import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReadService {
    final private PostRepository postRepository;


    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /*
            반환값 -> 리스트 [작성일자, 작성회원, 작성 게시물 수]

            select createdDate, memberId, count(id)
            from post
            where memberId = : memberId
              and createdDate between firstDate and lastDate
              gropu by createdDate and memberId
        */

        return postRepository.groupByCreatedDate(request);

    }
}
