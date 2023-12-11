package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Course;
import codemates.ajoucodexpert.domain.Homework;
import codemates.ajoucodexpert.dto.HomeworkDto;
import codemates.ajoucodexpert.repository.HomeworkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HomeworkServiceImpl {
    private final HomeworkRepository homeworkRepository;

    public Homework createHomework(HomeworkDto.Create dto, Course course) {
        log.info("과제 생성 : {} - {}", dto.getHomeworkIdx(), dto.getTitle());
        Homework.HomeworkId id = new Homework.HomeworkId(course.getId(), dto.getHomeworkIdx());
        Homework newHomework = new Homework(id, dto.getTitle(), dto.getContent(), dto.getEndDate(), 0, course);
        return homeworkRepository.save(newHomework);
    }

    public Homework updateHomework(HomeworkDto.Create dto, Homework homework) {
        log.info("과제 수정 : {}", dto.getTitle());
        homework.setTitle(dto.getTitle());
        homework.setContent(dto.getContent());
        homework.setEndDate(dto.getEndDate());
        return homeworkRepository.save(homework);
    }

    public Homework updateHomework(Homework homework, HomeworkDto.Create dto) {
        log.info("과제 수정 : {}", dto.getTitle());
        homework.setTitle(dto.getTitle());
        homework.setContent(dto.getContent());
        homework.setEndDate(dto.getEndDate());
        return homeworkRepository.save(homework);
    }

    public Homework updateHomework(Homework homework) {
        log.info("과제 수정 : {}", homework.getTitle());
        return homeworkRepository.save(homework);
    }

    public List<Homework> getHomeworks(Long courseId) {
        log.info("과제 조회 : {}", courseId);
        return homeworkRepository.findAllById_CourseId(courseId);
    }

    public Homework getHomework(Long courseId, Long homeworkIdx) {
        log.info("과제 조회 : {}.{}", courseId, homeworkIdx);
        return homeworkRepository.findById_CourseIdAndId_HomeworkIdx(courseId, homeworkIdx).orElse(null);
    }

    public void deleteHomework(Homework homework) {
        log.info("과제 삭제 : {}.{}", homework.getId().getCourseId(), homework.getId().getHomeworkIdx());
        homeworkRepository.delete(homework);
    }

    public Long getLastHomeworkIndex(Long courseId) {
        log.info("과제 마지막 인덱스 조회 : {}", courseId);
        Homework lastHw =  homeworkRepository.findTopById_CourseId(courseId).orElse(null);
        return lastHw == null ? 0 : lastHw.getId().getHomeworkIdx();
    }
}
