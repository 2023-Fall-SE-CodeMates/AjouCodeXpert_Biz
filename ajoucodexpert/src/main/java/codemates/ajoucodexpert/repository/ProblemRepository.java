package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Problem.ProblemId> {
    List<Problem> findAllById_CourseIdAndId_HomeworkIdx(Long courseId, Long homeworkIdx);
    Optional<Problem> findById_CourseIdAndId_HomeworkIdxAndId_ProblemIdx(Long courseId, Long homeworkIdx, Long problemIdx);
    Optional<Problem> findTopById_CourseIdAndId_HomeworkIdxOrderById_ProblemIdxDesc(Long courseId, Long homeworkIdx);
    Optional<Problem> findTopById_CourseIdAndId_HomeworkIdxOrderById_HomeworkIdxDesc(Long courseId, Long homeworkIdx);
}
