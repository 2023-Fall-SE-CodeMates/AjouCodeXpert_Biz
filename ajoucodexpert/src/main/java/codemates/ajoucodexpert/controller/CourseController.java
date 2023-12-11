package codemates.ajoucodexpert.controller;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.CourseMemberJoin;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.dto.CourseDto;
import codemates.ajoucodexpert.enums.Role;
import codemates.ajoucodexpert.exception.BusinessException;
import codemates.ajoucodexpert.exception.ExceptionType;
import codemates.ajoucodexpert.service.CourseMemberJoinManager;
import codemates.ajoucodexpert.service.CourseService;
import codemates.ajoucodexpert.service.MemberService;
import codemates.ajoucodexpert.service.OpenClassRequestManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseService courseService;
    private final MemberService memberService;
    private final OpenClassRequestManager openClassRequestManager;
    private final CourseMemberJoinManager courseMemberJoinManager;

    // 대회 생성하기 API
    @PostMapping
    public ResponseEntity<Object> createNewCourse(
            @RequestBody CourseDto.Create createDto,
            @AuthenticationPrincipal User user
            ) {
        log.info("반 개설 요청 : {}", user.getUsername());
        Member requester = memberService.getMember(user.getUsername());
        // requester.getAuthorities()에 관리자 및 TA 권한이 없다면 예외 발생
        if (!requester.getAuthorities().stream().toList().get(0).getCode().equals(Role.TA.getCode())) {
            throw new BusinessException(ExceptionType.UNAUTHORIZED, "TA 권한이 없습니다.");
        }
        // 반 개설 요청 생성
        openClassRequestManager.createRequest(requester, createDto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseDto.Element>> CourseListByCreator(
            @AuthenticationPrincipal User user
    ) {
        log.info("반 목록 조회 : {}", user.getUsername());

        Member member = memberService.getMember(user.getUsername());
        if (member == null) throw new BusinessException(ExceptionType.UNAUTHORIZED, "로그인이 필요합니다.");

        List<CourseDto.Element> courseList = new ArrayList<>();

        Authority memberAuth = member.getAuthority();
        if (memberAuth.getCode().equals(Role.TA.getCode())) {
            List<CourseMemberJoin> CreatorJoinList = courseMemberJoinManager.getJoinListByCreator(member);
            for (CourseMemberJoin join : CreatorJoinList) {
                courseList.add(new CourseDto.Element(join.getCourse().getId(), join.getCourse().getCode(), join.getCourse().getName(), true));
            }
            List<CourseMemberJoin> TAJoinList = courseMemberJoinManager.getJoinListByTa(member);
            for (CourseMemberJoin join : TAJoinList) {
                courseList.add(new CourseDto.Element(join.getCourse().getId(), join.getCourse().getCode(), join.getCourse().getName(), false));
            }
        }
        else if (memberAuth.getCode().equals(Role.STUDENT.getCode())) {
            List<CourseMemberJoin> StudentJoinList = courseMemberJoinManager.getJoinListByStudent(member);
            for (CourseMemberJoin join : StudentJoinList) {
                courseList.add(new CourseDto.Element(join.getCourse().getId(), join.getCourse().getCode(), join.getCourse().getName(), false));
            }
        }
        else {
            throw new BusinessException(ExceptionType.UNAUTHORIZED, "학생 또는 TA 권한이 필요합니다.");
        }





        return ResponseEntity.ok(courseList);
    }

}
