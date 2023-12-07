package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.UpdateRoleRequest;
import codemates.ajoucodexpert.domain.UserRequest;
import codemates.ajoucodexpert.repository.MemberRepository;
import codemates.ajoucodexpert.repository.UpdateRoleRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UpdateRoleRequestResolver implements UserRequestResolver {
    private final UpdateRoleRequestRepository updateRoleRequestRepository;
    private final MemberService memberService;

    @Override
    public void accept(UserRequest userRequest) {
        // 요청 객체의 상태를 승인으로 변경
        userRequest.accept();
        updateRoleRequestRepository.save((UpdateRoleRequest) userRequest);

        // 요청 객체의 사용자의 권한을 변경
        memberService.updateRole(userRequest.getRequester(), ((UpdateRoleRequest) userRequest).getAuthority());

    }

    @Override
    public void reject(UserRequest userRequest) {
        // 요청 객체의 상태를 거절로 변경
        userRequest.reject();
        updateRoleRequestRepository.save((UpdateRoleRequest) userRequest);
    }
}

