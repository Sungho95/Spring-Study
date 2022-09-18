package hellospring.springstudy.service;

import hellospring.springstudy.domain.repository.MemberRepository;
import hellospring.springstudy.domain.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스프링 빈 등록을 코드로 직접 하는 방법
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    
}
