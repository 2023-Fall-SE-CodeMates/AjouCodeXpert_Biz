package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Member;

public interface MemberService {
    // MemberFactory를 생성하는 메서드
    MemberFactory getMemberFactory();
    // studentCode를 통해 Member가 존재하는지 확인하는 메서드
    boolean isExistStudent(String studentCode);
    // loginId를 통해 Member가 존재하는지 확인하는 메서드
    boolean isExistId(String loginId);
    // loginId를 통해 Member를 가져오는 메서드
    Member getMember(String loginId);
    Member saveMember(Member member);
}
