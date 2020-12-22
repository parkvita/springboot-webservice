package com.parkvita.admin.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {

    /*
    * DAO 라고 불리는 DB layer 접근자
    * JPA 에선 Repository라고 부르며 인터페이스로 생성
    * 단순히 인터페이스를 생성 후, JpaRepository<Entity 클래스, pk 타입> 를 상송하면 기본적인 CRUE 메소드가 자동으로 생성
    *
    * 주의점은 Entity클래스와 기본 EntityRepository는 함께 위치해야함 그래서 도메인패키지에서 함께관리
    * */

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}
