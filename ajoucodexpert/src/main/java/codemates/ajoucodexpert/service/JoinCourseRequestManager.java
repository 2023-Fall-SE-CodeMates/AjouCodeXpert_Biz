package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Course;
import codemates.ajoucodexpert.domain.CourseMemberRole;
import codemates.ajoucodexpert.domain.JoinCourseRequest;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.repository.JoinCourseRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class JoinCourseRequestManager implements UserRequestService {
    private final JoinCourseRequestRepository joinCourseRequestRepository;

    // 반 참가 요청 객체를 생성하는 메서드
    public JoinCourseRequest createRequest(Member requester, Course course, CourseMemberRole role) {
        log.info("수강 신청 시작 : {} -> {}, role = {}", requester.getLoginId(), course.getCode(), role.getCode());
        JoinCourseRequest newRequest = new JoinCourseRequest(requester, course, role);
        return joinCourseRequestRepository.save(newRequest);
    }

    public JoinCourseRequest getRequest(Long id) {
        log.info("수강 신청 조회 : {}", id);
        return joinCourseRequestRepository.findById(id).orElse(null);
    }

    public List<JoinCourseRequest> getUnresolvedRequests(Long courseId) {
        log.info("미결된 수강 신청 조회");
        return joinCourseRequestRepository.findAllByCourseIdAndRequestStatus(courseId, 0);
    }

    public JoinCourseRequest getUnresolvedRequest(Course course, Member member) {
        log.info("미결된 수강 신청 조회");
        return joinCourseRequestRepository.findByCourseIdAndRequesterAndRequestStatus(course.getId(), member, 0).orElse(null);
    }
}
