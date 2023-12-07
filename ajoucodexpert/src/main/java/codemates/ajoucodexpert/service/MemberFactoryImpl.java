package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.Major;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.StudentInfo;
import codemates.ajoucodexpert.dto.MemberDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberFactoryImpl implements MemberFactory{

    private final PasswordEncoder passwordEncoder;

    @Override
    public Member createMember(MemberDto.Signup signupDto, Set<Authority> authorities, StudentInfo studentInfo, Major major) {
        log.debug("새 Member 생성");

        signupDto.setPw(passwordEncoder.encode(signupDto.getPw()));

        return Member.builder()
                .loginId(signupDto.getId())
                .pw(signupDto.getPw())
                .name(signupDto.getName())
                .authorities(authorities)
                .studentInfo(studentInfo)
                .major(major)
                .build();
    }
}
