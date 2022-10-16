package hellospring.springstudy.service;

import hellospring.springstudy.repository.JdbcMemberRepository;
import hellospring.springstudy.repository.JpaMemberRepository;
import hellospring.springstudy.repository.MemberRepository;
import hellospring.springstudy.repository.JdbcTemplateMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

// 스프링 빈 등록을 코드로 직접 하는 방법
@Configuration
public class SpringConfig {

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}
