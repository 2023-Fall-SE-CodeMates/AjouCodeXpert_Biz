package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Course;
import codemates.ajoucodexpert.domain.CourseMemberJoin;
import codemates.ajoucodexpert.domain.CourseMemberRole;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.repository.CourseMemberJoinRepository;
import codemates.ajoucodexpert.repository.CourseMemberRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CourseMemberJoinManager {
    private final CourseMemberJoinRepository courseMemberJoinRepository;
    private final CourseMemberRoleRepository courseMemberRoleRepository;
    CourseMemberJoin createJoin(CourseMemberJoin newJoin) {
        log.info("반 참가 로직 실행 : {} -> {}, role = {}", newJoin.getMember(), newJoin.getCourse(), newJoin.getRole());
        return courseMemberJoinRepository.save(newJoin);
    }
    CourseMemberJoin getJoinByMemberAndCourse(Member member, Course course) {
        return null;
    }
}
