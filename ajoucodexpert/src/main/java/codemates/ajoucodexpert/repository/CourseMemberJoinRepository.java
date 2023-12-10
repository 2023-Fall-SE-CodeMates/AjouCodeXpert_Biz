package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.Course;
import codemates.ajoucodexpert.domain.CourseMemberJoin;
import codemates.ajoucodexpert.domain.CourseMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseMemberJoinRepository extends JpaRepository<CourseMemberJoin, CourseMemberJoin.JoinId> {
    // TA가 개설한 & 학생이 참여한 반 목록 Join 리스트
    List<CourseMemberJoin> findAllByIdMemberIdAndRoleCode(String memberId, Integer roleCode);
    List<CourseMemberJoin> findAllByIdMemberId(String id_memberId);
    // 특정 반에 Join된 사용자 리스트
    List<CourseMemberJoin> findAllByIdCourseId(Long courseId);
    List<CourseMemberJoin> findAllByIdCourseIdAndHiddenIsFalse(Long courseId);
    Optional<CourseMemberJoin> findByIdCourseIdAndIdMemberId(Long courseId, String memberId);
}
