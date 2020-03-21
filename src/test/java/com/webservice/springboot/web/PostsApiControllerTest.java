package com.webservice.springboot.web;

import com.webservice.springboot.domain.posts.Posts;
import com.webservice.springboot.domain.posts.PostsRepository;
import com.webservice.springboot.web.dto.PostsSaveRequestDto;
import com.webservice.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)//스프링부트와 Jnuit 연결
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //JPA 기능 테스트 , 호스트가 사용하지 않는 랜덤 포트를 사용하겠다.
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate; //JPA 기능 테스트
    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        //given(준비)
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("duswns1783@naver.com")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";
        //when(실행)
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() throws Exception{
        //given(준비)
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("duswns1783@naver.com")
                .build());
        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();
        String url = "http://localhost:"+port + "/api/v1/posts/"+updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);


        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,
                requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }
}
