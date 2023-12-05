package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public MemberFactory getMemberFactory() {
        return new MemberFactoryImpl();
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
        return null;
    }

    @Override
    public Member saveMember(Member unsaved) {
        Member saved = memberRepository.save(unsaved);
        log.info("새 Member 저장: {}", saved.getLoginId());
        return saved;
    }
}
