package com.parkvita.admin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/* Application 클래스는 앞으로 만들 프로젝트의 메인 클래스
*  @SpringBootApplication 으로 인해 스프링 부트의 자동설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
*  특히나 annotation 이 있는 위치부터 설정을 읽어가기 때문에 이클래스는 항상 프로젝트 최상단에 위치
* */
@EnableJpaAuditing  // JPA Auditing 활성화
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
        /* 위 메소드로 인해 내장 WAS 실행 외부 WAS를 두지않고 애플리케이션 실행할때 내부에서 WAS 실행*/
        /* 항상 톰캣을 설치할 필요 없이 스프링부트로 만들어진 jar 파일 실행*/
        /* 내장 WAS 쓰는 이유는 언제 어디서나 같은 환경에서 스프링부트를 배포하기 위해*/
    }
}
