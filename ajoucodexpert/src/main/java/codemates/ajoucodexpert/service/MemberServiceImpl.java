package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public MemberFactory getMemberFactory() {
        return new MemberFactoryImpl(new BCryptPasswordEncoder());
    }

    @Override
    public boolean isExistStudent(String studentCode) {
        return memberRepository.existsByStudentInfo_Code(studentCode);
    }

    @Override
    public boolean isExistId(String loginId) {
        return memberRepository.existsById(loginId);
    }

    @Override
    public Member getMember(String loginId) {
        return memberRepository.findById(loginId).orElse(null);
    }

    @Override
    public Member saveMember(Member unsaved) {
        Member saved = memberRepository.save(unsaved);
        log.info("새 Member 저장: {}", saved.getLoginId());
        return saved;
    }

    @Override
    public void updateRole(Member member, Authority authority) {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        member.setAuthorities(authorities);
        log.info("Member 권한 변경: {}", member.getLoginId());
    }
}
