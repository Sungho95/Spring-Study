# MVC와 템플릿 엔진
## MVC: Model, View, Controller
템플릿 엔진을 모델, 뷰, 컨트롤러로 나누어 뷰를 템플릿 엔진으로 인해 렌더링 된 상태로 사용자에게 전달하는 방식

Controller
```java
@Controller
public class HelloController {
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
}
```

View
```html
<html xmlns:th="http://www.thymeleaf.org">
<body>
<p th:text="'hello ' + ${name}">hello! empty</p>
</body>
</html>
```
