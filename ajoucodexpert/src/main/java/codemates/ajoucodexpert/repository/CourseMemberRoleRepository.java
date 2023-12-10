package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.CourseMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseMemberRoleRepository extends JpaRepository<CourseMemberRole, Integer> {
    Optional<CourseMemberRole> findByCode(Integer code);
}
