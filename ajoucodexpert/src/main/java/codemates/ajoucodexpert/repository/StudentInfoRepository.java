package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, String> {
}
