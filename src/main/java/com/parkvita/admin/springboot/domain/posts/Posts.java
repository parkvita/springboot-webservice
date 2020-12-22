package com.parkvita.admin.springboot.domain.posts;

import com.parkvita.admin.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 클래스내 모든 필드의 getter메소드 자동생성
@NoArgsConstructor  // 기본생성자 자동 추가, public Posts(){} 와 같은효과
@Entity // 테이블과 링크될 클래스임을 나타냄, 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블이름을 매칭
        // ex) SalesManager.java -> sales_manager table
public class Posts extends BaseTimeEntity {    /* 실제 DB의 테이블과 매칭될 클래스로 entity 클래스라고도 함
    JPA를 사용하면 DB에 데이터 작업할경우 실제 쿼리를 날리기보단 이 Entity클래스의 수정을 통해 작업
*/

    @Id // 해당 테이블의 PK 필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK생성 규칙을 나타냄
                                                        // 스프링부트2.0부터는 GenerationType.IDENTITY옵션을 추가해야만 auto_increment가 됨
    private Long id;                                    // 웬만하면 Entity의 pk는 long타입의 auto_increment사용 추천 (mysql기준 이렇게하면 bigint) 주민등록번호등 비즈니스상 유니크한 키 또는 복합키로 pk 할경우 난감한상황 종종 발생
                                                        // fk 맺을때 다른테이블에서 복하비를 전부 갖고있거나, 중간 테이블을 하나 둬야하는 상황 발생, 인덱스에 좋지 않음, 유니크한 조건이 변경될경우 pk 전체수정
                                                        // 따라서 주민번호, 복합키 등은 유니크키로 별도로 추가하기 추천

    @Column(length = 500, nullable = false) // 테이블의 컬럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 됨
                                            // 사용 이유는 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
                                            // ex) 문자열의 경우 기본 varchar(255)지만 사이즈를 500으로 늘리고 싶다거나 타입을 TEXT로 변경하고 싶거나 할때 사용
                                            // author는 특정지을게 없어서 @Column 사용하지 않았음
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder    // 해당클래스의 빌더 패턴 클래스 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
/*
* 이 클래스엔 Setter 메소드가 없다
* 자바 빈 규약을 생각하면서 getter/setter 을 무작정 생성하는 경우가 있다. 이는 해당 클래스의 인스턴스값들이 언제 어디서 변해야 하는지 코드상으로 명확하게 구분할수가 없어 차후 기능변경시 힘듬
* 그래서 Entity 클래스에서는 절대 Setter 메소드를 만들지 않고 대신 해당 필드의 값 변경이 필요한 경우 명확히 그 의도와 목적을 나타낼수 있는 메소드를 추가
*
* */