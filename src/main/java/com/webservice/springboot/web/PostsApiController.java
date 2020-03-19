package com.webservice.springboot.web;

import com.webservice.springboot.service.PostsService;
import com.webservice.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor  //final 키워드 필드에 대한 생성자를 만든다.
@RestController //컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 준다.
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

}
