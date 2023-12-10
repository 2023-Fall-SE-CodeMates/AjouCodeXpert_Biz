package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseInfoRepository extends JpaRepository<CourseInfo, String> {
    Optional<CourseInfo> findByCode(String code);
}
