package hellospring.springstudy.controller;

import hellospring.springstudy.domain.Member;
import hellospring.springstudy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

}
// Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아준다.