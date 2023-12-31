package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseInfoRepository extends JpaRepository<CourseInfo, Long> {
    Optional<CourseInfo> findByCode(Long code);
}
