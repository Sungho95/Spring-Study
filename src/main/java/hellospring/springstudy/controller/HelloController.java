package hellospring.springstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 정적 컨텐츠
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    // MVC
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // API
    @GetMapping("hello-string")
    @ResponseBody // HTTP 통신 프로토콜의 데이터를 직접 넣겠다는 의미의 어노테이션
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // 일반 문자를 보낼 때 StringConverter
    }

    // JSON 방식 {key : value}
    @GetMapping("hello-api")
    @ResponseBody // 기본으로 ResponseBody 어노테이션을 사용하면, JSON 방식을 사용하는게 좋다.
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello(); // 객체 자체를 보낼 때 JsonConverter
        hello.setName(name);
        return hello;
    }

    // Getter, Setter : JavaBean 규약, 클래스의 변수가 private일 때, 외부에서 접근하지 못하는 것을 메서드를 통해 접근 가능하도록 함.
   static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
