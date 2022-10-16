package hellospring.springstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 실행 후 http://localhost:8080/ 진입시
    // 스프링 컨테이너를 확인한 후, 정적 페이지를 확인하기 때문에
    // 우선순위로 인해 index.html 페이지가 열리지 않게 된다.
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
