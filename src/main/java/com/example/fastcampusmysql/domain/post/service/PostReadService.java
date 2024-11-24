package com.example.fastcampusmysql.domain.post.service;


import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.utill.CursorRequest;
import com.example.fastcampusmysql.utill.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Post> getPosts(Long memberId, Pageable pageRequest){
        return  postRepository.findAllByMemberId(memberId, pageRequest);

    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        var posts = findAllBy(memberId, cursorRequest);
        var nextKey = posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }
        return postRepository.findAllMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());

    }
}
