package codemates.ajoucodexpert.controller;

import codemates.ajoucodexpert.domain.*;
import codemates.ajoucodexpert.dto.JoinCourseRequestDto;
import codemates.ajoucodexpert.dto.OpenClassRequestDto;
import codemates.ajoucodexpert.dto.UpdateRoleRequestDto;
import codemates.ajoucodexpert.enums.CourseRole;
import codemates.ajoucodexpert.enums.Role;
import codemates.ajoucodexpert.exception.BusinessException;
import codemates.ajoucodexpert.exception.ExceptionType;
import codemates.ajoucodexpert.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
@Slf4j
public class UserRequestController {
    private final UpdateRoleRequestResolver updateRoleRequestResolver;
    private final UpdateRoleRequestManager updateRoleRequestManager;
    private final OpenClassRequestResolver openClassRequestResolver;
    private final OpenClassRequestManager openClassRequestManager;
    private final JoinCourseRequestManager joinCourseRequestManager;
    private final JoinCourseRequestResolver joinCourseRequestResolver;
    private final CourseMemberJoinManager courseMemberJoinManager;
    private final MemberService memberService;
    private final CourseService courseService;
    private final AuthorityService authorityService;

    @GetMapping("/role")
    public ResponseEntity<List<UpdateRoleRequestDto.Element>> getUpdateRoleRequestList() {
        log.info("회원 권한 변경 요청 목록 조회");

        List<UpdateRoleRequestDto.Element> unresolved = new ArrayList<>();
        for (UpdateRoleRequest request : updateRoleRequestManager.getUnresolvedRequests()) {
            unresolved.add(UpdateRoleRequestDto.Element.of(request));
        }
        return ResponseEntity.ok(unresolved);
    }

    @PostMapping("/role")
    public ResponseEntity<Object> processUpdateRoleRequest(
            @RequestParam("requestId") Long requestId,
            @RequestParam("action") Long action,
            @AuthenticationPrincipal User user) {

        log.debug("회원 권한 변경 요청 처리 : {} -> {}", user.getUsername(), requestId);
        UpdateRoleRequest request = updateRoleRequestManager.getRequest(requestId);

        if (request == null) throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "존재하지 않는 요청 아이디입니다.");

        if (action == 1) {
            updateRoleRequestResolver.accept(request);
        }
        else if (action == -1) {
            updateRoleRequestResolver.reject(request);
        }
        else {
            throw new BusinessException(ExceptionType.INVALID_INPUT, "권한 변경 요청 인자가 잘못되었습니다.");
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/open")
    public ResponseEntity<List<OpenClassRequestDto.Element>> getOpenClassRequestList() {
        log.info("반 개설 요청 목록 조회");

        List<OpenClassRequestDto.Element> unresolved = new ArrayList<>();

        for (OpenClassRequest request : openClassRequestManager.getUnResolvedRequest()) {
            unresolved.add(OpenClassRequestDto.Element.of(request));
        }
        return ResponseEntity.ok(unresolved);
    }

    @PostMapping("/course/open")
    public ResponseEntity<Object> processOpenClassRequest(
            @RequestParam("requestId") Long requestId,
            @RequestParam("action") Long action,
            @AuthenticationPrincipal User user) {

        log.debug("반 개설 요청 처리 : {} -> {}", user.getUsername(), requestId);
        OpenClassRequest request = openClassRequestManager.getRequest(requestId);

        if (request == null) throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "존재하지 않는 요청 아이디입니다.");

        if (action == 1) {
            openClassRequestResolver.accept(request);
        }
        else if (action == -1) {
            openClassRequestResolver.reject(request);
        }
        else {
            throw new BusinessException(ExceptionType.INVALID_INPUT, "권한 변경 요청 인자가 잘못되었습니다.");
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * 반 참가 요청 API
     * @param joinCode
     * @param user
     * @return
     */
    @PostMapping("/course/join/new")
    public ResponseEntity<Object> createJoinCourseRequest(
            @RequestParam("joinCode") String joinCode,
            @AuthenticationPrincipal User user) {

        log.info("반 가입 요청 : {} -> {}", user.getUsername(), joinCode);

        Member member = memberService.getMember(user.getUsername());

        // joinCode로 course 조회
        Course course = courseService.getCourse(joinCode);

        // BusinessException
            // 존재하지 않는 경우
        if (course == null) throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "존재하지 않는 반입니다.");
            // 이미 가입된 경우
        if (alreadyJoinedCourse(member, course)) throw new BusinessException(ExceptionType.DATA_ALREADY_EXIST, "이미 가입된 반입니다.");
            // 이미 요청한 경우
        if (alreadySentJoinRequest(member, course)) throw new BusinessException(ExceptionType.DATA_ALREADY_EXIST, "이미 가입 요청한 반입니다.");

        // 가입 요청 생성
        CourseMemberRole role = null;
            // 요청자가 학생 권한을 가질 경우
        if (member.getAuthority().getCode().equals(Role.STUDENT.getCode()))
            role = new CourseMemberRole(CourseRole.STUDENT);
            // 요청자의 TA 권한을 가질 경우
        else if (member.getAuthority().getCode().equals(Role.TA.getCode()))
            role = new CourseMemberRole(CourseRole.TA);

        if (role == null) throw new BusinessException(ExceptionType.INVALID_INPUT, "잘못된 요청입니다.");

        // 가입 요청 저장
        joinCourseRequestManager.createRequest(member, course, role);

        return ResponseEntity.created(null).build();

    }

    /**
     * 반 가입 요청 조회 API
     * @param courseId
     * @return
     */
    @GetMapping("/course/join")
    public ResponseEntity<List<JoinCourseRequestDto.Element>> getJoinCourseRequest(
            @RequestParam("courseId") Long courseId) {

        log.info("반 가입 요청 조회 : {}", courseId);

        Course course = courseService.getCourse(courseId);

        // BusinessException
        if (course == null) throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "존재하지 않는 반입니다.");

        // 가입 요청 조회
        List<JoinCourseRequestDto.Element> unresolved = new ArrayList<>();
        for (JoinCourseRequest request : joinCourseRequestManager.getUnresolvedRequests(course.getId())) {
            unresolved.add(JoinCourseRequestDto.Element.of(request));
        }

        return ResponseEntity.ok(unresolved);
    }

    /**
     * 반 가입 요청 처리 API
     * @param requestId
     * @param action
     * @return
     */
    @PostMapping("/course/join")
    public ResponseEntity<Object> processJoinCourseRequest(
            @RequestParam("requestId") Long requestId,
            @RequestParam("action") Long action) {

        log.info("반 가입 요청 처리 : {}", requestId);

        JoinCourseRequest request = joinCourseRequestManager.getRequest(requestId);

        if (request == null) throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "존재하지 않는 요청 아이디입니다.");

        if (action == 1) {
            joinCourseRequestResolver.accept(request);
        }
        else if (action == -1) {
            joinCourseRequestResolver.reject(request);
        }
        else {
            throw new BusinessException(ExceptionType.INVALID_INPUT, "권한 변경 요청 인자가 잘못되었습니다.");
        }

        return ResponseEntity.noContent().build();
    }

    private boolean alreadySentJoinRequest(Member member, Course course) {
        return joinCourseRequestManager.getUnresolvedRequest(course, member) != null;
    }

    private boolean alreadyJoinedCourse(Member member, Course course) {
        return courseMemberJoinManager.getJoinByMemberAndCourse(member, course) != null;
    }

}
