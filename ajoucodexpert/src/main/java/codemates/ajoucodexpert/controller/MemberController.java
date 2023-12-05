package codemates.ajoucodexpert.controller;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.Major;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.StudentInfo;
import codemates.ajoucodexpert.dto.MemberDto;
import codemates.ajoucodexpert.exception.BusinessException;
import codemates.ajoucodexpert.exception.ExceptionType;
import codemates.ajoucodexpert.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final StudentInfoService studentInfoService;
    private final MajorService majorService;
    private final AuthorityService authorityService;

    @PostMapping("/signup")
    public ResponseEntity<MemberDto.Signup> createMember(@RequestBody MemberDto.Signup signupDto) {
        log.debug("회원가입 요청 받음 : {}", signupDto.getId());
        StudentInfo studentInfo = null;
        Major major = null;
        Set<Authority> authorities = new HashSet<>();
        MemberFactory mf;

        try {
            // 입력한 학번이 DB에 존재하는지 확인
            studentInfo = studentInfoService.findByStudentId(signupDto.getStudentId());
            if (studentInfo == null) throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "잘못된 학번입니다.");
            // 입력한 전공 코드가 DB에 존재하는지 확인
            major = majorService.findByMajorCode(signupDto.getMajorCode());
            if (major == null) throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "존재하지 않는 전공 코드입니다.");
            // 권한 설정
            Authority auth = authorityService.getAuthorityByCode(signupDto.getRoleCode());
            if (auth == null) throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "존재하지 않는 권한 코드입니다.");
            else authorities.add(auth);
            // 입력한 학번으로 가입한 회원이 있는지 확인
            if (memberService.isExistStudent(signupDto.getStudentId()))
                throw new BusinessException(ExceptionType.DATA_ALREADY_EXIST, "이미 가입된 학번입니다.");
            // 입력한 아이디가 DB에 존재하는지 확인
            if (memberService.isExistId(signupDto.getId()))
                throw new BusinessException(ExceptionType.DATA_ALREADY_EXIST, "이미 존재하는 아이디입니다.");

        } catch (Exception e) {
            log.error("회원가입 실패 : {}", e.getMessage());
        }

        // memberFactory를 통해 회원 생성
        mf = memberService.getMemberFactory();

        // 회원 생성
        Member created = mf.createMember(signupDto, authorities, studentInfo, major);
        memberService.saveMember(created);

        return ResponseEntity.noContent().build();
    }
}
