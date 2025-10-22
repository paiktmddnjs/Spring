package com.kh.spring.service;

import com.kh.spring.model.mapper.MemberMapper;
import com.kh.spring.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //@Component보다 더 구체화해서 service객체에 알맞게 bean등록
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public Member getMemberById(String memberId) {
        return memberMapper.getMemberById(memberId);
    }

    @Override
    public int getMemberCountById(String memberId) {
        return memberMapper.getMemberCountById(memberId);
    }

    @Override
    public int addMember(Member member) {
        /*
        MyBatis를 spring없이 단독으로 사용할 때에는 수동으로 커밋또는 롤백을 해줘야한다.
        spring과 함께 사용할 때에는 트랜잭션처리를 스프링이 자동으로 관리한다.
         */
        return memberMapper.addMember(member);
    }
}