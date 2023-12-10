package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Course;
import codemates.ajoucodexpert.domain.CourseMemberJoin;
import codemates.ajoucodexpert.domain.CourseMemberRole;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.enums.CourseRole;
import codemates.ajoucodexpert.repository.CourseMemberJoinRepository;
import codemates.ajoucodexpert.repository.CourseMemberRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CourseMemberJoinManager {
    private final CourseMemberJoinRepository courseMemberJoinRepository;
    private final CourseMemberRoleRepository courseMemberRoleRepository;
    public CourseMemberJoin createJoin(CourseMemberJoin newJoin) {
        log.info("반 참가 로직 실행 : {} -> {}, role = {}", newJoin.getMember(), newJoin.getCourse(), newJoin.getRole());
        return courseMemberJoinRepository.save(newJoin);
    }
    public CourseMemberJoin getJoinByMemberAndCourse(Member member, Course course) {
        return null;
    }
    //
    public List<CourseMemberJoin> getJoinListByCreator(Member member) {
        log.info("TA가 개설한 반 목록 조회 : {}", member);
        return courseMemberJoinRepository.findAllByIdMemberIdAndRoleCode(member.getLoginId(), CourseRole.CREATOR.getCode());
    }

    public List<CourseMemberJoin> getJoinListByTa(Member member) {
        log.info("TA가 참여한 반 목록 조회 : {}", member);
        return courseMemberJoinRepository.findAllByIdMemberIdAndRoleCode(member.getLoginId(), CourseRole.TA.getCode());
    }
    
    public List<CourseMemberJoin> getJoinListByCreatorAndTa(Member member) {
        log.info("학생이 참여한 반 목록 조회 : {}", member);
        List<CourseMemberJoin> creatorJoinList = courseMemberJoinRepository.findAllByIdMemberIdAndRoleCode(member.getLoginId(), CourseRole.CREATOR.getCode());
        List<CourseMemberJoin> taJoinList = courseMemberJoinRepository.findAllByIdMemberIdAndRoleCode(member.getLoginId(), CourseRole.TA.getCode());
        creatorJoinList.addAll(taJoinList);
        return creatorJoinList;
    }

    public List<CourseMemberJoin> getJoinListByStudent(Member member) {
        log.info("학생이 참여한 반 목록 조회 : {}", member);
        return courseMemberJoinRepository.findAllByIdMemberIdAndRoleCode(member.getLoginId(), CourseRole.STUDENT.getCode());
    }
}
