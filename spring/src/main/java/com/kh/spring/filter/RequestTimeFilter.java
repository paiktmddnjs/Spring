package com.kh.spring.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
/*
    로깅(Logging)이란?
    실행중에 발생하는 상태, 동작, 예외, 요청흐름을 파악하거나 기록할 때 사용하는 것.
    개발중에는 디버깅용으로 사용하고, 운영중에는 에러를 분석하는 용도로 사용.

    System.out.println()
    : 출력이 무조건 콘솔에서 진행되고, 로그 구분이 불가하며, 출력용으로 문자열연산 성능이 좋지 못하다.
      로그를 기록하는 용도로 사용시 관리가 어렵고 분석도 어렵다.

    log.info()
    log.debug()
    log.error()
    : 콘솔, 파일, 외부 시스템등 다양하게 출력할 수 있고 로그를 구분하여 출력할 수 있음(INFO, DEBUG, ERROR등...)
      로그를 파일로 관리할 수 있기 떄문에 검색하고 분석하기가 용이. 성능 손실이 적다.

      SLF4J : 로깅 인터페이스(표준) -> 어떤 구현체를 사용할지는 어떤 라이브러리를 설치하냐에 따라 달라짐.
      Logback : 실제 로그를 파일, 콘솔에 출력하는 구현체

      spring boot에서는 별도의 설정없이 SLF4J + Logback 조합의 사용이 적용된다.
 */

/*
    요청~응답까지의 시간을 측정하는 필터
    모든 HTTP요청에 대해서 처리.
 */
@Slf4j
@Component
public class RequestTimeFilter implements Filter {

    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequestTimeFilter.class); -> @Slf4j 어노테이션을 작성시 Lombok이 자동으로 생성

    //해당 필터에서 사용할 로직을 작성하는 메서드
    //해당 메서드는 DispatcherServlet 호출 전에 실행 됨.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //요청시작 시간 기록
        long startTime = System.currentTimeMillis();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String fullUrl = request.getRequestURI() + (queryString != null ? "?" + queryString : "");

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            //요청 종료 시간 기록
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            int status = response.getStatus(); //완료된 상태 코드

            log.info("[{}] {} - Status: {} - duration: {}ms", method, fullUrl, status, duration);
        }


    }
}