package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.CourseMemberJoin;
import codemates.ajoucodexpert.domain.JoinCourseRequest;
import codemates.ajoucodexpert.domain.UserRequest;
import codemates.ajoucodexpert.repository.JoinCourseRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JoinCourseRequestResolver implements UserRequestResolver {
    private final CourseMemberJoinManager courseMemberJoinManager;
    private final JoinCourseRequestRepository joinCourseRequestRepository;


    @Override
    public void accept(UserRequest userRequest) {
        // 요청 객체의 상태를 승인으로 변경
        userRequest.accept();
        joinCourseRequestRepository.save((JoinCourseRequest) userRequest);

        // CourseMemberJoin 객체 생성
        // (JoinCourseRequest) userRequest에서 Course, Member, CourseMemberRole 객체를 가져와서 CourseMemberJoin 객체 생성
        JoinCourseRequest request = (JoinCourseRequest) userRequest;
        CourseMemberJoin.JoinId joinId = new CourseMemberJoin.JoinId(request.getCourse().getId(), request.getRequester().getLoginId());
        CourseMemberJoin newJoin = new CourseMemberJoin(joinId, request.getCourse(), request.getRequester(), request.getRole(), false);
        courseMemberJoinManager.createJoin(newJoin);
    }

    @Override
    public void reject(UserRequest userRequest) {
        // 요청 객체의 상태를 거절로 변경
        userRequest.reject();
        joinCourseRequestRepository.save((JoinCourseRequest) userRequest);
    }
}
