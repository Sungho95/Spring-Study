package hellospring.springstudy.controller;

import hellospring.springstudy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // Autowired 필드 주입
    // @Autowired private MemberService memberService;
    private final MemberService memberService;

    // Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아준다.
    // 생성자 주입
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

}
