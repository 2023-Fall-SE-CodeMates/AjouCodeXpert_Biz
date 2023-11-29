package codemates.ajoucodexpert.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository {
    Optional<Member> findById(String loginId);
}
