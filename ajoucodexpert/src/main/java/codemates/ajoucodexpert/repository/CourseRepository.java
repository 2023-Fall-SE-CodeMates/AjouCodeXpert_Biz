package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long id);

    Optional<Course> findByJoinCode(String joinCode);
}
