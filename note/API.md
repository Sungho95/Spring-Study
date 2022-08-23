# API
@ResponseBody의 사용

1. HTTP의 Body에 문자 내용을 직접 반환
2. viewResolver 대신에 HttpMessageConverter가 동작
3. 기본 문자처리 : StringHttpMessageConverter
4. 기본 객체처리 : MappingJackson2HttpMessageConverter
5. byte 처리 등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음.

참고 : 클라이언트의 HTTP Accept 헤더와 서버의 컨트롤러 반환 타입 정보를 조합하여
HttpMessageConverter가 선택된다.