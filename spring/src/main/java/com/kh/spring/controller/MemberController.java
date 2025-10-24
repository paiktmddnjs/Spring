package com.kh.spring.controller;

import com.kh.spring.model.mapper.MemberMapper;
import com.kh.spring.model.vo.Member;
import com.kh.spring.service.MemberService;
import com.kh.spring.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;

//Bean에 class등록하는 방법으로 @Component를 클래스에 부여한다.
//@Controller -> @Component + Controller객체가 가질 수 있는 예외처리등의 기능을 포함하는 어노테이션
@Controller
public class MemberController {

    /*
        private MemberService memberService; = new MemberServiceImpl();
        기존 객체 직접 생성방식
        객체간의 결합도가 높아짐(구현체가 고정되어있어 확장/교체가 어려움) -> 소스코드 수정이 일어날 경우 하나하나 변경해야하는 코드가 연쇄적으로 생김.

        DI(Dependancy Injection) - 의존성 주입방식 사용
        - 객체를 직접 생성하지않고, 스프링컨테이너가 관리하는 객체를 주입받아 쓰는 것
        - 결합도 낮아지고, 테스트가 용이해지며, 관심사 분리(객체를 생성하고 사용하는 부분과 비즈니스 로직이 분리)되어 유지보수성이 높아짐.

        @Autowired
        의존성 주입을 사용할 때 기술하는 어노테이션
        클래스내에 필요한 객체를 직접생성하지않고 spring컨테이너가 관리하는 객체(Bean에 등록)를 주입받아 사용할 수 있게 해줌.
        필드 주입방식 / 생성자 주입방식

        필드주입방식
        스프링 컨테이너가 객체를 생성 후, @Autowired가 있는 필드에 의존성주입 해주는 방식
        장점 : 코드가 간결하다.
        단점 : 테스트가 어렵다.(필드주입방식은 객체생성시 의존성이 주입되지 않고 bean에서 생성 후 주입받는 방식이 때문에
                                테스트 진행시 임의로 객체를 생성하기 어렵다,)
              불변성 보장 불가. -> 런타임에 값이 변경될 수 있다.

       생성자 주입방식
       가장 권장되는 방식으로, 생성 시점에 @Autowired어노테이션이 있는 생성자를 통해 의존성을 주입하는 방식
       - 불변성 보장, 테스트 용이
     */
    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberController(MemberService memberService,  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberService = memberService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    /*
    Spring에서 클라이언트가 HTML 폼데이터로 보낸 정보를 받는 방법

    1. HttpServletRequest를 활용해서 전달값을 가져옴.
    메서드에 매게변수로 HttpServletRequest를 작성해주면
    스프링컨테이너가 해당 메서드를 호출할 때 자동으로 매게변수로 주입
     */
    /*
    @PostMapping("login.me")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("userId");
        String pw = request.getParameter("userPwd");
        System.out.println(id);
        System.out.println(pw);

        return null;
    }
     */

    /*
    2. @RequestParam 어노테이션을 활용하는 방법
    request.getParameter(키)로 벨류를 추출하는 역할을 대신 해주는 어노테이션
    요청  parameter의 key값과 동일하게 매게변수명을 설정해주면 @RequestParam어노테이션을 생략해도 됨.
     */
    /*
    @PostMapping("login.me")
    public String login(@RequestParam(value = "userId", defaultValue = "user01") String id, String userPwd){
        System.out.println(id);
        System.out.println(userPwd);

        return null;
    }
     */

    /*
    3. 객체를 이용하는 방법(@ModelAttribute 생략가능)
    요청시 전달값들을 담고자하는 클래스 타입의 객체를 만들어 준 뒤
    전달되는 key값과 매게변수 객체의 필드명을 동일하게 만들어주면 객체에 전달값을 맵핑해준다.
     */
    /*
    @PostMapping("login.me")
    public String login(@ModelAttribute Member member){
        System.out.println(member);
        return null;
    }
     */

    /*
    요청 처리 후 데이터를 담아서 응답하는 방법(포워딩 or url재요청)

    1. spring에서 제공하는 Model객체를 이용하는 방법
    포워딩할 응답뷰로 전달하고자하는 데이터를 k-v쌍으로 담을 수 있는 영역
    Model객체에 addAttribute()로 저장시 requestScope에 값을 저장하게 됨.
     */
    /*
    @PostMapping("login.me")
    public String login(@ModelAttribute Member member, Model model) {
        System.out.println(member);

        model.addAttribute("memberId", member.getMemberId());
        model.addAttribute("memberPwd", member.getMemberPwd());

        return "index";
    }
     */

    /*
    2. HttpSession을 이용한 값 저장 후 url 재요청
     */
    /*
    @PostMapping("login.me")
    public String login(@ModelAttribute Member member, HttpSession session) {
        System.out.println(member);

        session.setAttribute("memberId", member.getMemberId());
        session.setAttribute("memberPwd", member.getMemberPwd());

        return "redirect:/";
    }
    */

    /*
        3. ModelAndView객체를 이용하는 방법 -> 데이터를 담고 리턴형식까지 지정할 수 있음
     */
    /*
    @PostMapping("login.me")
    public ModelAndView login(@ModelAttribute Member member, ModelAndView mv) {
        System.out.println(member);

        mv.addObject("memberId", member.getMemberId());
        mv.addObject("memberPwd", member.getMemberPwd());

        //mv.setViewName("index"); // 포워딩
        mv.setViewName("redirect:/"); //url재요청

        return mv;
    }
     */

    @PostMapping("login.me")
    public ModelAndView login(String memberId, String memberPwd, HttpSession httpSession, ModelAndView mv) {
        Member loginMember = memberService.getMemberById(memberId);
        System.out.println(loginMember);

        //memberPwd -> 암호화 되지 않은 pwd(평문)
        //loginMember.getMemberPwd() -> 암호화 된 pwd
        //bCryptPasswordEncoder.matches(평문, 암호문) -> 해당 비밀번호가 암호화된 비밀번호와 일치하면 true/ 아니면 false반환

        if(loginMember == null) { //ID가 잘못된 상태
            mv.addObject("errorMsg", "아이디를 찾을 수 없습니다.");
            mv.setViewName("common/error");
            //} else if(!loginMember.getMemberPwd().equals(memberPwd)){ //비밀번호 오류
        } else if(!bCryptPasswordEncoder.matches(memberPwd, loginMember.getMemberPwd())){
            mv.addObject("errorMsg", "비밀번호를 확인해 주세요.");
            mv.setViewName("common/error");
        } else {//로그인 성공
            httpSession.setAttribute("loginMember", loginMember);
            mv.setViewName("redirect:/");
        }

        return mv;
    }


    @GetMapping("enrollForm.me")
    public String enrollForm() {
        return "member/enrollForm";
    }


    @GetMapping("idDulpicateCheck.me")
    @ResponseBody //리턴을 뷰(jsp)로 보내지말고, HTTP응답 바디에 그대로 담아서 보내라.
    public String idDulpicateCheck(@RequestParam String checkId) {

        int count = memberService.getMemberCountById(checkId);

        return count > 0 ? "NNNNN" : "NNNNY";
    }

    @PostMapping("insert.me")
    public String joinMember(Member member, HttpSession httpSession, Model model) {
        /*
            비밀번호를 사용자 입력 그대로 저장한다 -> 평문 -> 해킹의 우려와 개인정보 침해에 우려가 있음.
            스프링시큐리티에서 지원하는 암호화방식을 사용해서 저장/검증
         */

        String pwd = bCryptPasswordEncoder.encode(member.getMemberPwd());
        member.setMemberPwd(pwd);

        int result = memberService.addMember(member);

        if(result > 0){
            httpSession.setAttribute("alertMsg", "회원가입에 성공하였습니다.");
            return "redirect:/";
        } else {
            model.addAttribute("errorMsg", "회원가입에 실패하였습니다.");
            return "common/error";
        }
    }

}