package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.JoinCourseRequest;
import codemates.ajoucodexpert.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinCourseRequestRepository extends UserRequestRepository<JoinCourseRequest>{
    List<JoinCourseRequest> findAllByRequestStatus(int requestStatus);
    List<JoinCourseRequest> findAllByCourseIdAndRequestStatus(Long courseId, int requestStatus);
    Optional<JoinCourseRequest> findByCourseIdAndRequesterAndRequestStatus(Long courseId, Member requester, int requestStatus);
}
