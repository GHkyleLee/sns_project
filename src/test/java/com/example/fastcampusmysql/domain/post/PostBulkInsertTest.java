package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.utill.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=ENC(VZhejcju4bUHjZ8hBjjMBig/npOZ46sW/YZFhca4rDt21rlpc+wkGcKrTxe1c4+Jii1Q4d+t1yDVBAPtXsIi7b6rmGVtW/3sZBtt1oS/4iXNzlsWqGyqOSkrs8UUX0bnh0DdMWs2H+3LQ61cnPe+gwM/oc8kMxoRjfK9kEgmzgd+8ggs/ETGSwYl5tLMm6BYNPQMDi633VKNuVFZZ0+lNdBxNHv0SniZ4088MeybQjsj7zKEtUi7jtucU9zV5K3O)",
        "spring.datasource.username=ENC(bor7u0DMJb9EfFcAJrfoiw==)",
        "spring.datasource.password=ENC(K6Z/rcw/nMF/I5tcTJnSHxYocDIK1RW/)",
        "jasypt.encryptor.key=ghlee1215",
        "jasypt.encryptor.bean=jasyptEncryptor"
})
public class PostBulkInsertTest {
    @Autowired
    private PostRepository postRepository;

    @Test

    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(
                2L,
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,5,5)
        );
//        IntStream.range(0,5)
//                .mapToObj(i -> easyRandom.nextObject(Post.class))
//                .forEach(x ->
//                                postRepository.save(x)
//                        );

        var stopWatch = new StopWatch();
        stopWatch.start();
        var posts = IntStream.range(0,10000 * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();
        stopWatch.stop();
        System.out.println("객체 생성시간 :" + stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();

        queryStopWatch.start();

        postRepository.bulkInsert(posts);

        queryStopWatch.stop();
        System.out.println("쿼리  시간 :" +   queryStopWatch.getTotalTimeSeconds());

    }

}
