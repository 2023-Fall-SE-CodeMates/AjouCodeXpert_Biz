package codemates.ajoucodexpert.controller;

import codemates.ajoucodexpert.domain.*;
import codemates.ajoucodexpert.dto.HomeworkDto;
import codemates.ajoucodexpert.dto.ProblemDto;
import codemates.ajoucodexpert.enums.CourseRole;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class HomeworkController {
    private final HomeworkServiceImpl homeworkService;
    private final CourseService courseService;
    private final CourseMemberJoinManager courseMemberJoinManager;
    private final MemberService memberService;
    private final ProblemServiceImpl problemService;

    // 과제 조회하기 API
    @GetMapping("/course/{courseId}/homework")
    public ResponseEntity<List<HomeworkDto.Element>> getHomeworks (
            @PathVariable Long courseId,
            @AuthenticationPrincipal User user
    ) {
        log.info("과제 조회 요청 : {}", courseId);

        Course course = courseService.getCourse(courseId);
        if (course == null) {
            throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "해당 반을 찾을 수 없습니다.");
        }

        List<HomeworkDto.Element> homeworks = new ArrayList<>();

        CourseMemberRole courseRole = courseMemberJoinManager.getJoinByMemberAndCourse(memberService.getMember(user.getUsername()), course).getRole();
        Boolean removable = courseRole.getCode().equals(CourseRole.TA.getCode()) || courseRole.getCode().equals(CourseRole.CREATOR.getCode());

        for (Homework homework : homeworkService.getHomeworks(courseId)) {
            HomeworkDto.Element e = HomeworkDto.Element.of(homework);
            e.setRemovable(removable);
            homeworks.add(e);

        }

        return ResponseEntity.ok(homeworks);
    }

    // 과제 생성하기 API
    @PostMapping("/course/{courseId}/homework")
    public ResponseEntity<Object> createNewHomework(
            @PathVariable Long courseId,
            @RequestBody HomeworkDto.Create createDto
    ) {
        log.info("과제 생성 요청 : {}", courseId);

        Course course = courseService.getCourse(courseId);
        if (course == null) {
            throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "해당 반을 찾을 수 없습니다.");
        }

        // 과제 생성
        createDto.setHomeworkIdx(homeworkService.getLastHomeworkIndex(courseId) + 1);
        Homework createdHw = homeworkService.createHomework(createDto, course);

        // 과제에 문제 추가
        long nextIdx = problemService.getLastProblemIdx(courseId, createdHw.getId().getHomeworkIdx()) + 1;
        for (ProblemDto.Detail problem : createDto.getProblems()) {
            problem.setIndex(nextIdx++);
            problemService.createProblem(problem, createdHw);
            // 추가된 문제만큼 총점 추가
            createdHw.setTotalScore(createdHw.getTotalScore() + problem.getPoints());
            homeworkService.updateHomework(createdHw);
        }


        return ResponseEntity.created(null).build();
    }

    @GetMapping("/course/{courseId}/homework/{homeworkIdx}")
    public ResponseEntity<HomeworkDto.Detail> getHomework(
            @PathVariable Long courseId,
            @PathVariable Long homeworkIdx,
            @AuthenticationPrincipal User user
    ) {
        log.info("과제 조회 요청 : {} -> {}", courseId, homeworkIdx);

        Homework homework = homeworkService.getHomework(courseId, homeworkIdx);
        Member member = memberService.getMember(user.getUsername());
        Course course = courseService.getCourse(courseId);

        if (homework == null) {
            throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "해당 과제를 찾을 수 없습니다.");
        }

        HomeworkDto.Detail homeworkDto = HomeworkDto.Detail.of(homework);
        CourseMemberRole courseRole = courseMemberJoinManager.getJoinByMemberAndCourse(member, course).getRole();
        boolean removable = courseRole.getCode().equals(CourseRole.TA.getCode()) || courseRole.getCode().equals(CourseRole.CREATOR.getCode());
        homeworkDto.setRemovable(removable);

        // 과제에 포함된 문제들 조회
        List<ProblemDto.Detail> problems = new ArrayList<>();
        for (Problem problem : problemService.getProblems(courseId, homeworkIdx)) {
            ProblemDto.Detail problemDto = ProblemDto.Detail.of(problem);
            problemDto.setRemovable(removable);
            problems.add(problemDto);
        }
        homeworkDto.setProblems(problems);

        return ResponseEntity.ok(homeworkDto);
    }

    // 과제 수정하기 API
    @PutMapping("/course/{courseId}/homework/{homeworkIdx}")
    public ResponseEntity<Object> updateHomework(
            @PathVariable Long courseId,
            @PathVariable Long homeworkIdx,
            @RequestBody HomeworkDto.Create updateDto
    ) {
        log.info("과제 수정 요청 : {} -> {}", courseId, homeworkIdx);

        Homework homework = homeworkService.getHomework(courseId, homeworkIdx);

        if (homework == null) {
            throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "해당 과제를 찾을 수 없습니다.");
        }

        homeworkService.updateHomework(homework, updateDto);

        // 원래 과제에 포함된 문제 인덱스 리스트를 추출
        List<Problem> originalProblems = problemService.getProblems(courseId, homeworkIdx);
        List<Long> originalProblemIdxList = problemService.getProblemIdxList(courseId, homeworkIdx);

        // 새로운 DTO에 포함된 문제 인덱스 리스트를 추출
        List<Long> newProblemIdxList = updateDto.getProblems().stream()
                .map(ProblemDto.Detail::getIndex)
                .toList();
        // 두 리스트를 비교하여 새로운 문제가 추가되었는지, 삭제되었는지, 수정되었는지 확인
        // 1. 삭제
        for (Problem problem : originalProblems) {
            if (!newProblemIdxList.contains(problem.getId().getProblemIdx())) {
                // 총점 감소
                homework.setTotalScore(homework.getTotalScore() - problem.getScore());
                problemService.deleteProblem(problem);
            }
        }
        // 2. 수정
        for (ProblemDto.Detail problemDto : updateDto.getProblems()) {
            if (problemDto.getIndex() != null && originalProblemIdxList.contains(problemDto.getIndex())) {
                Problem problem = problemService.getProblem(courseId, homeworkIdx, problemDto.getIndex());
                // 총점 변경
                homework.setTotalScore(homework.getTotalScore() - problem.getScore() + problemDto.getPoints());
                problemService.updateProblem(problemDto, problem);
            } else {
                // 3. 추가
                problemDto.setIndex(problemService.getLastProblemIdx(courseId, homeworkIdx) + 1);
                // 총점 증가
                homework.setTotalScore(homework.getTotalScore() + problemDto.getPoints());
                problemService.createProblem(problemDto, homework);
            }
        }


        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/course/{courseId}/homework/{homeworkIdx}")
    public ResponseEntity<Object> deleteHomework(
            @PathVariable Long courseId,
            @PathVariable Long homeworkIdx
    ) {
        log.info("과제 삭제 요청 : {} -> {}", courseId, homeworkIdx);

        Homework homework = homeworkService.getHomework(courseId, homeworkIdx);

        if (homework == null) {
            throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "해당 과제를 찾을 수 없습니다.");
        }

        homeworkService.deleteHomework(homework);

        return ResponseEntity.noContent().build();
    }
}
