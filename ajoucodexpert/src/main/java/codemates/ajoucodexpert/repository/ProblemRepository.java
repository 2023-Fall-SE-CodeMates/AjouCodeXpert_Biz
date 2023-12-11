package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Problem.ProblemId> {
}
