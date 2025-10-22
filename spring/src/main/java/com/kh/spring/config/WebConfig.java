package com.kh.spring.config;

import com.kh.spring.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC설정을 위한 Configuration 클래스
 * WebMvcConfigurer 인터페이스를 구현하여 spring mvc의 다양한 설정을 커스터마이징 할 수 있음.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*
         인터셉터를 등록하는 메서드
         여러개의 인터페이스를 등록할 수 있고, 등록 순서대로 실행됨.
         InterceptorRegistry를 통해 인터셉터를 등록
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns(
                        "/myPage.me",
                        "/update.me",
                        "/delete.me"
                ).excludePathPatterns("/login.me", "/update.me", "/delete.me");
    }
}