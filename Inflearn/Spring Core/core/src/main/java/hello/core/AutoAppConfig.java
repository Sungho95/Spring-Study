package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //AppConfig.java 파일의 Configuration을 제외하기 위한 설정
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // 탐색 위치 지정(Default: ComponentScan이 위치한 패키지부터)
//        basePackages = {"hello.core.member", "hello.core.service"},
//        basePackageClasses = AutoAppConfig.class
)
public class AutoAppConfig {
    // 수동 등록 vs 자동 등록
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}