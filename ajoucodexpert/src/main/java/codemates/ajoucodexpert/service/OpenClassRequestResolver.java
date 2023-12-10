package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.*;
import codemates.ajoucodexpert.enums.CourseRole;
import codemates.ajoucodexpert.exception.BusinessException;
import codemates.ajoucodexpert.exception.ExceptionType;
import codemates.ajoucodexpert.repository.CourseMemberRoleRepository;
import codemates.ajoucodexpert.repository.OpenClassRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OpenClassRequestResolver implements UserRequestResolver {
    private final OpenClassRequestRepository openClassRequestRepository;
    private final CourseMemberJoinManager courseMemberJoinManager;
    private final CourseService courseService;


    @Override
    public void accept(UserRequest userRequest) {
        // 요청 객체의 상태를 승인으로 변경
        userRequest.accept();
        openClassRequestRepository.save((OpenClassRequest) userRequest);

        // Course 객체 생성
        Course newCourse = new Course(null, ((OpenClassRequest) userRequest).getCourseCode(), ((OpenClassRequest) userRequest).getCourseName(), null);
        newCourse = courseService.createCourse(newCourse);

        // CourseMemberJoin 객체 생성 (role = creator)
        CourseMemberJoin.JoinId joinId = new CourseMemberJoin.JoinId(newCourse.getId(), userRequest.getRequester().getLoginId());
        CourseMemberRole role = new CourseMemberRole(CourseRole.CREATOR);
        CourseMemberJoin newJoin = new CourseMemberJoin(joinId, newCourse, userRequest.getRequester(), role, false);
        courseMemberJoinManager.createJoin(newJoin);

    }

    @Override
    public void reject(UserRequest userRequest) {
        // 요청 객체의 상태를 거절로 변경
        userRequest.reject();
        openClassRequestRepository.save((OpenClassRequest) userRequest);
    }
}
