package com.kh.spring.model.vo;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private int memberNo;
    private String memberId;
    private String memberPwd;
    private String memberName;
    private String phone;
    private String email;
    private String address;
    private String interest;
    private Date enrollDate;
    private Date modifyDate;
    private String status;

    public static Member insertCreateMember(String userId,
                                            String userPwd,
                                            String userName,
                                            String phone,
                                            String email,
                                            String address,
                                            String interest) {
        Member m = new Member();
        m.setMemberId(userId);
        m.setMemberPwd(userPwd);
        m.setMemberName(userName);
        m.setPhone(phone);
        m.setEmail(email);
        m.setAddress(address);
        m.setInterest(interest);

        return m;
    }

    public static Member updateCreateMember(String userId,
                                            String phone,
                                            String email,
                                            String address,
                                            String interest) {
        Member m = new Member();
        m.setMemberId(userId);
        m.setPhone(phone);
        m.setEmail(email);
        m.setAddress(address);
        m.setInterest(interest);

        return m;
    }
}