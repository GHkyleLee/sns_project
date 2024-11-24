package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.utill.CursorRequest;
import com.example.fastcampusmysql.utill.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    final private PostWriteService postWriteService;
    final private PostReadService postReadService;

    @PostMapping("")
    public Long create(PostCommand command) {
        /*
        * 게시글 등록
        * */
        return postWriteService.create(command);
    }

    @GetMapping("/daiily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("members/{memberId}")
    public Page<Post> getPosts(
            @RequestParam Long memberId,
            Pageable pageable


    ) {
        return postReadService.getPosts(memberId, pageable);
    }
    @GetMapping("members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(
            @RequestParam Long memberId,
            CursorRequest cursorRequest


    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }
}
