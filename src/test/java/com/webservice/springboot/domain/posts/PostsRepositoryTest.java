package com.webservice.springboot.domain.posts;
//save, findAll 기능 테스트

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) //테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
                            //여기서는 SpringRunner라는 스프링 실행자를 사용.
                            //즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 합니다.
@SpringBootTest //스프링을 실행시켜야 함,H2데이터베이스를 자동으로 실행해줌.
public class PostsRepositoryTest {

    @Autowired //스프링이 관리하는 빈을 주입받는다.
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given(준비)
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()  //테이블 posts에 insert/update를 해준다.  id 값이 있으면 update, 없다면 insert 쿼리 실행.
                .title(title)
                .content(content)
                .author("duswns1783@naver.com")
                .build());
        //when(실행)
        List<Posts> postsList = postsRepository.findAll(); //테이블 posts에 있는 모든 데이터를 조회해오는 메소드이다.

        //then(검증)
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2020,3,21,0,0,0);
        postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author("author")
            .build());
        //when
        List<Posts> postsList = postsRepository.findAll();
        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>>> createDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
