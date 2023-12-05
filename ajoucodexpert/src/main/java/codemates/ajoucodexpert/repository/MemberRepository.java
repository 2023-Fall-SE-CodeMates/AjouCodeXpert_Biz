package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findById(String loginId);
    boolean existsById(String loginId);
    boolean existsByStudentInfo(StudentInfo studentInfo);
}
