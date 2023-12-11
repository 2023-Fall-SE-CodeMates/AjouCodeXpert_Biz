package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.Problem;
import codemates.ajoucodexpert.domain.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, TestCase.TestCaseId> {
    void deleteAllByProblem(Problem problem);
}
