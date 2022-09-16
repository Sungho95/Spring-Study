package hellospring.springstudy;

// 스프링 빈을 등록하는 방법
// 컴포넌트 스캔과 자동 의존관계 설정
// 자바 코드로 직접 스프링 빈 등록하기

// @Component : 스프링 빈을 등록해주는 것
// 스프링을 실행시키는 Application이 설정된 패키지의 하위 패키지까지만 등록 해줌
// 컴포넌트는 @Service, @Controller, @Repository에 포함되어 있음
// 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본적으로 싱글톤으로 등록한다.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringStudyApplication.class, args);
	}

}
