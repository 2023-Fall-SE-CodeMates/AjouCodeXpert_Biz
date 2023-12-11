package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Homework.HomeworkId> {
    Optional<Homework> findTopById_CourseId(Long courseId);
    List<Homework> findAllById_CourseId(Long courseId);
    Optional<Homework> findById_CourseIdAndId_HomeworkIdx(Long courseId, Long homeworkIdx);
}
