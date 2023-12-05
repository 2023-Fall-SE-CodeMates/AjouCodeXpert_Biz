package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Major;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.StudentInfo;
import codemates.ajoucodexpert.dto.MemberDto;
import codemates.ajoucodexpert.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberFactoryImpl implements MemberFactory{
    private final MemberRepository memberRepository;


    @Override
    public Member createMember(MemberDto.Signup signupDto, StudentInfo studentInfo, Major major) {
        log.debug("새 Member 생성");
        Member newMember =  Member.builder()
                .loginId(signupDto.getId())
                .pw(signupDto.getPw())
                .name(signupDto.getName())
                .studentInfo(studentInfo)
                .major(major)
                .build();

        return saveMember(newMember);
    }

    @Override
    public Member saveMember(Member unsaved) {
        Member saved = memberRepository.save(unsaved);
        log.info("새 Member 저장: {}", saved.getLoginId());
        return saved;
    }
}
