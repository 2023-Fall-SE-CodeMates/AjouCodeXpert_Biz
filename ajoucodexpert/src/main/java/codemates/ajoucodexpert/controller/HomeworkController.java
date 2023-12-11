package codemates.ajoucodexpert.controller;

import codemates.ajoucodexpert.domain.Course;
import codemates.ajoucodexpert.domain.Homework;
import codemates.ajoucodexpert.dto.HomeworkDto;
import codemates.ajoucodexpert.exception.BusinessException;
import codemates.ajoucodexpert.exception.ExceptionType;
import codemates.ajoucodexpert.service.CourseService;
import codemates.ajoucodexpert.service.HomeworkServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class HomeworkController {
    private final HomeworkServiceImpl homeworkService;
    private final CourseService courseService;

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

        createDto.setHomeworkIdx(homeworkService.getLastHomeworkIndex(courseId) + 1);
        homeworkService.createHomework(createDto, course);

        return ResponseEntity.created(null).build();
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
