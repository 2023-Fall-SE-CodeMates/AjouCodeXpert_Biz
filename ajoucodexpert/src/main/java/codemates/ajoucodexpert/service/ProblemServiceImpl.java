package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Course;
import codemates.ajoucodexpert.domain.Homework;
import codemates.ajoucodexpert.domain.Problem;
import codemates.ajoucodexpert.dto.ProblemDto;
import codemates.ajoucodexpert.repository.ProblemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProblemServiceImpl {
    private final ProblemRepository problemRepository;

    public List<Problem> getProblems(Long courseId) {
        log.info("문제 조회 : {}", courseId);
        return problemRepository.findAllById_CourseId(courseId);
    }

    public Problem createProblem(ProblemDto.Detail dto, Homework homework) {
        log.info("문제 생성 : {} - {}", dto.getProblemIdx(), dto.getTitle());
        Problem.ProblemId id = new Problem.ProblemId(homework.getId().getCourseId(), homework.getId().getHomeworkIdx(), dto.getProblemIdx());
        Problem newProblem = new Problem(id, dto.getTitle(), dto.getDescription(), dto.getScore(), dto.getLangCode(), homework);
        return problemRepository.save(newProblem);
    }

    public Problem updateProblem(ProblemDto.Detail dto, Problem problem) {
        log.info("문제 수정 : {}", dto.getTitle());
        problem.setTitle(dto.getTitle());
        problem.setDescription(dto.getDescription());
        problem.setScore(dto.getScore());
        problem.setLanguage(dto.getLangCode());
        return problem;
    }

    public void deleteProblem(Problem problem) {
        log.info("문제 삭제 : {}.{}.{}", problem.getId().getCourseId(), problem.getId().getHomeworkIdx(), problem.getId().getProblemIdx());
        problemRepository.delete(problem);
    }

    // 과제에 속한 문제 인덱스 목록 조회
}
