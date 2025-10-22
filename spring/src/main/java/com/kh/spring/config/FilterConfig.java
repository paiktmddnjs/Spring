package com.kh.spring.config;

import com.kh.spring.filter.RequestTimeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//필터 등록 및 설정을 위한 Configuration 클래스
@Configuration
public class FilterConfig {

    /*
        기본적으로 필터는 @Component어노테이션으로 Bean에 등록만 해줘도 사용이 가능하지만
        좀더 디테일한 설정을 할 때는 FilterRegistrationBean이라는 객체를 사용하면 된다.
     */

    @Bean
    public FilterRegistrationBean<RequestTimeFilter> filterRegistrationBean(RequestTimeFilter filter) {
        FilterRegistrationBean<RequestTimeFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(filter); //실행할 필터객체를 등록
        registration.addUrlPatterns("/*"); //모든 url 패턴에 대해서 필터를 적용하겠다.
        registration.setOrder(1); // 필터 우선 순위 -> 숫자가 낮을수록 먼저 실행
        registration.setName("requestTimeFilter");

        return registration;
    }
}