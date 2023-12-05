package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.Major;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.StudentInfo;
import codemates.ajoucodexpert.dto.MemberDto;

import java.util.Set;

public interface MemberFactory {
    // MemberDto.Sigup, StudentInfo, Major -> Member를 생성하는 메서드
    Member createMember(MemberDto.Signup signupDto, Set<Authority> authorities, StudentInfo studentInfo, Major major);
}
