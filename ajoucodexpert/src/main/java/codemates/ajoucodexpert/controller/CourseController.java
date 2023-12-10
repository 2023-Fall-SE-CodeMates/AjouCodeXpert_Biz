package codemates.ajoucodexpert.controller;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.dto.CourseDto;
import codemates.ajoucodexpert.enums.Role;
import codemates.ajoucodexpert.exception.BusinessException;
import codemates.ajoucodexpert.exception.ExceptionType;
import codemates.ajoucodexpert.service.CourseService;
import codemates.ajoucodexpert.service.MemberService;
import codemates.ajoucodexpert.service.OpenClassRequestManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseService courseService;
    private final MemberService memberService;
    private final OpenClassRequestManager openClassRequestManager;

    @PostMapping
    public ResponseEntity<Object> createNewCourse(
            @RequestBody CourseDto.Create createDto,
            @AuthenticationPrincipal User user
            ) {
        log.info("반 개설 요청 : {}", user.getUsername());
        Member requester = memberService.getMember(user.getUsername());
        // requester.getAuthorities()에 관리자 및 TA 권한이 없다면 예외 발생
        if (!requester.getAuthorities().stream().toList().get(0).getCode().equals(1)) {
            throw new BusinessException(ExceptionType.UNAUTHORIZED, "TA 권한이 없습니다.");
        }
        // 반 개설 요청 생성
        openClassRequestManager.createRequest(requester, createDto);

        return ResponseEntity.noContent().build();
    }
}
