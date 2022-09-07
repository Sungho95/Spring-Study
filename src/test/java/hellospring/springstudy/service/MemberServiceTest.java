package hellospring.springstudy.service;

import hellospring.springstudy.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// 테스트는 빌드할 때 실제 코드에 포함되지 않음.
class MemberServiceTest {

    MemberService memberService = new MemberService();

    @Test // 테스트는 메서드를 한글로 작성 가능
    void 회원가입() {
        // given (값이 주어졌을 때)
        Member member = new Member();
        member.setName("hello");

        // when (어떻게 실행을 하며)
        Long saveId = memberService.join(member);

        // then (어떤 결과를 얻을 수 있는가)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}