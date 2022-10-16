package hellospring.springstudy.controller;

import hellospring.springstudy.domain.Member;
import hellospring.springstudy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class MemberController {

    // Autowired 필드 주입 : 필드에 주입할 경우, 바꿀 수 있는 방법이 없음
    // @Autowired private MemberService memberService;
    private final MemberService memberService;

//    // @Autowired Setter주입 : setMemberService를 pyblic으로 해야하기 때문에 노출이 되는 단점이 있음
//    private MemberService memberService;
//
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
        // memberService.setMemberRepository(); <- 문구를 통해 누구나 접근할 수 있게된다.
//    }

    // Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아준다.
    // 생성자 주입 : 가장 추천하는 방식
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

//        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String List(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
