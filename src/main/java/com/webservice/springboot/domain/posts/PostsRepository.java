package com.webservice.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//Posts클래스로 Database를 접근하게 해준다.
//보통 Dao라고 불리는 DB Layer 접근자!
//Dao를 JPA에선 Repository라고 부르며 인터페이스로 생성.
//JapRepository<Entity 클래스, PK 타입>을 상속!
public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDESC();


}
